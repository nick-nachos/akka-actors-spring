package net.nachos.actors.di.beans

import akka.actor.{ActorRef, ActorRefFactory, ActorSystem, Props}
import net.nachos.actors.di
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ParentActorFactory @Autowired() (createChildActor: di.ParentActor.ChildActorFactory)
  extends di.ActorApp.impl.ParentActorFactory {

  override def apply(system: ActorSystem, name: String): ActorRef =
    system.actorOf(Props(new di.ParentActor(createChildActor)), name)
}

@Component
class ChildActorFactory @Autowired() (randomNumberService: di.RandomNumberService)
  extends di.ParentActor.ChildActorFactory {

  override def apply(context: ActorRefFactory, name: String): ActorRef =
    context.actorOf(Props(new di.ChildActor(randomNumberService)), name)
}