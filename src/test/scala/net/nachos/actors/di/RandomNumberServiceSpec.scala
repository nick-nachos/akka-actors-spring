package net.nachos.actors.di

import akka.actor.{ActorSystem, ExtendedActorSystem, PoisonPill}
import akka.testkit.{TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import net.nachos.actors.di.RandomNumberService.impl.RandomNumberServiceActor
import net.nachos.actors.di.RandomNumberService.{GetRandomNumber, RandomNumber}
import net.nachos.actors.di.beans.{RandomNumberServiceActorFactory, RandomNumberServiceConfiguration}
import org.mockito.scalatest.MockitoSugar
import org.scalatest.concurrent.Eventually
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.SpanSugar
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}

class RandomNumberServiceSpec
  extends TestKit(ActorSystem("RandomNumberServiceActorSpec", ConfigFactory.load("unit-test.conf")))
    with AnyFlatSpecLike with Matchers with BeforeAndAfterAll with BeforeAndAfterEach
    with Eventually with SpanSugar with MockitoSugar {

  private def createRandomNumberServiceActorFactory(): RandomNumberServiceActorFactory =
    new RandomNumberServiceActorFactory()

  private def extendedSystem: ExtendedActorSystem = system
    .asInstanceOf[ExtendedActorSystem]

  override protected def afterEach(): Unit = {
    system.actorSelection("/system/*") ! PoisonPill
    super.afterEach()
  }

  override protected def afterAll(): Unit = {
    system.terminate()
    super.afterAll()
  }

  behavior of classOf[RandomNumberServiceActorFactory].getName

  it should "create a system actor" in {
    val createRandomNumberServiceActor = createRandomNumberServiceActorFactory()
    val mockSystem = mock[ExtendedActorSystem]
    val actorName = "actor1"
    val probe = TestProbe()
    when(mockSystem.systemActorOf(any, eqTo(actorName))).thenReturn(probe.ref)
    val actorRef = createRandomNumberServiceActor(mockSystem, actorName)
    actorRef shouldBe probe.ref
    eventually(timeout(1.second), interval(100.millis)) {
      verify(mockSystem, times(1)).systemActorOf(any, eqTo(actorName))
    }
  }

  behavior of classOf[RandomNumberServiceActor].getName

  it should "generate random numbers upon request" in {
    val createRandomNumberServiceActor = createRandomNumberServiceActorFactory()
    val actor = createRandomNumberServiceActor(extendedSystem, "actor2")
    val probe = TestProbe()

    for (i <- 1 to 100) {
      probe.send(actor, GetRandomNumber)
      withClue(s"Attempt #$i failed.") {
        val response = probe.expectMsgType[RandomNumber]
        response.num should be >= 0.0
        response.num should be <= 1.0
      }
    }
  }

  behavior of classOf[RandomNumberServiceConfiguration].getName

  it should s"create a ${classOf[RandomNumberService].getSimpleName}" in {
    val mockRandomNumberServiceActorFactory = mock[RandomNumberServiceActorFactory]
    val probe = TestProbe()
    when(mockRandomNumberServiceActorFactory.apply(extendedSystem, "random")).thenReturn(probe.ref)

    val service = new RandomNumberServiceConfiguration(mockRandomNumberServiceActorFactory, extendedSystem).service()
    service.serviceActor shouldBe probe.ref
  }
}
