package net.nachos.actors.di

import akka.actor.{Actor, ActorRef, ActorRefFactory}
import net.nachos.actors.di.ParentActor.ChildActorFactory

object ParentActor {

  trait ChildActorFactory {

    def apply(context: ActorRefFactory, name: String): ActorRef
  }
}

class ParentActor(createChildActor: ChildActorFactory) extends Actor {

  createChildActor(context, "child")

  override def receive: Receive =
    Actor.ignoringBehavior
}
