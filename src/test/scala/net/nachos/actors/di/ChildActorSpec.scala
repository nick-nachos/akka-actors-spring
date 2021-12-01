package net.nachos.actors.di

import akka.actor.ActorSystem
import akka.testkit.{TestKit, TestProbe}
import com.typesafe.config.{Config, ConfigFactory}
import net.nachos.actors.di.beans.ChildActorFactory
import org.mockito.scalatest.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpecLike

class ChildActorSpec
  extends TestKit(ActorSystem("ChildActorSpec", ConfigFactory.load(ChildActorSpec.testConfig)))
    with AnyFlatSpecLike with BeforeAndAfterAll with MockitoSugar {

  override protected def afterAll(): Unit = {
    system.terminate()
    super.afterAll()
  }

  behavior of classOf[ChildActor].getName

  it should "poll the random number generator periodically" in {
    val probe = TestProbe()
    val mockRandomNumberService = mock[RandomNumberService]
    when(mockRandomNumberService.serviceActor).thenReturn(probe.ref)
    new ChildActorFactory(mockRandomNumberService)(system, "child1")
    probe.expectMsg(RandomNumberService.GetRandomNumber)
    probe.expectNoMessage()
    probe.reply(RandomNumberService.RandomNumber(0))
    probe.expectMsg(RandomNumberService.GetRandomNumber)
  }
}

object ChildActorSpec {

  def testConfig: Config =
    ConfigFactory.parseString("net.nachos.actors.di.child-actor.random-query-interval = 100ms")
      .withFallback(ConfigFactory.parseResources("unit-test.conf"))
}
