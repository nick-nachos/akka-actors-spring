package net.nachos.actors.di.beans

import akka.actor.{ActorRef, ExtendedActorSystem, Props}
import net.nachos.actors.di.RandomNumberService
import net.nachos.actors.di.RandomNumberService.impl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.stereotype.Component

@Configuration
class RandomNumberServiceConfiguration @Autowired() (createSystemActor: RandomNumberServiceActorFactory,
                                                     actorSystem: ExtendedActorSystem) {

  @Bean
  def service(): RandomNumberService =
    new impl.RandomNumberServiceImpl(createSystemActor, actorSystem)
}

@Component
class RandomNumberServiceActorFactory extends impl.RandomNumberServiceActorFactory {

  override def apply(system: ExtendedActorSystem, name: String): ActorRef =
    system.systemActorOf(Props(new impl.RandomNumberServiceActor()), name)
}
