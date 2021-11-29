package net.nachos.actors.di.beans

import akka.actor.ActorSystem
import net.nachos.actors.di.ActorApp
import net.nachos.actors.di.ActorApp.impl
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}

@Configuration
@ComponentScan
class ActorAppConfiguration(createParentActor: impl.ParentActorFactory, system: ActorSystem) {

  @Bean
  def actorApp(): ActorApp =
    new impl.ActorAppImpl(createParentActor, system)

}
