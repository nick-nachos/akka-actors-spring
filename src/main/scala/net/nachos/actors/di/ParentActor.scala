package net.nachos.actors.di

import akka.actor.{Actor, ActorContext, ActorRef}
import net.nachos.actors.di.ParentActor.ChildActorFactory

object ParentActor {

  trait ChildActorFactory {

    def apply(context: ActorContext, name: String): ActorRef
  }
}

class ParentActor(createChildActor: ChildActorFactory) extends Actor {

  createChildActor(context, "child")

  override def receive: Receive =
    Actor.ignoringBehavior
}
