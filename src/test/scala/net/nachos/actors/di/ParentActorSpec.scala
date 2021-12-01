package net.nachos.actors.di

import akka.actor.ActorSystem
import akka.testkit.TestKit
import com.typesafe.config.ConfigFactory
import net.nachos.actors.di.ParentActor.ChildActorFactory
import net.nachos.actors.di.beans.ParentActorFactory
import org.mockito.scalatest.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.Eventually
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.time.SpanSugar

class ParentActorSpec
  extends TestKit(ActorSystem("ParentActorSpec", ConfigFactory.load("unit-test.conf")))
    with AnyFlatSpecLike with BeforeAndAfterAll with MockitoSugar with Eventually with SpanSugar {

  override protected def afterAll(): Unit = {
    system.terminate()
    super.afterAll()
  }

  behavior of classOf[ParentActor].getName

  it should "create a child" in {
    val mockChildActorFactory = mock[ChildActorFactory]
    new ParentActorFactory(mockChildActorFactory)(system, "parent1")
    eventually(timeout(1.second), interval(100.millis)) {
      verify(mockChildActorFactory, times(1)).apply(any, eqTo("child"))
    }
  }

}
