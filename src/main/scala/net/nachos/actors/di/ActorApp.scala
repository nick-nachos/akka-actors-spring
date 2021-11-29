package net.nachos.actors.di

import akka.actor.{ActorRef, ActorSystem}

trait ActorApp {

  def start(): Unit

  def stop(): Unit
}

object ActorApp {

  object impl {

    trait ParentActorFactory {

      def apply(system: ActorSystem, name: String): ActorRef
    }

    class ActorAppImpl(createParentActor: ParentActorFactory, system: ActorSystem) extends ActorApp {

      override def start(): Unit = {
        createParentActor(system, "parent")
      }

      override def stop(): Unit = {
        system.terminate()
      }
    }
  }
}
