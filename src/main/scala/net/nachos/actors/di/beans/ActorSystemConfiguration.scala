package net.nachos.actors.di.beans

import akka.actor.{ActorSystem, ExtendedActorSystem}
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class ActorSystemConfiguration {

  @Bean
  def actorSystem(): ExtendedActorSystem =
    ActorSystem("my-app").asInstanceOf[ExtendedActorSystem]
}
