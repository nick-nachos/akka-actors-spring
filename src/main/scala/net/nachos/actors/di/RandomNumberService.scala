package net.nachos.actors.di

import akka.actor.{Actor, ActorLogging, ActorRef, ExtendedActorSystem}

import scala.util.Random

trait RandomNumberService {

  val serviceActor: ActorRef
}

object RandomNumberService {

  case object GetRandomNumber

  case class RandomNumber(num: Double)

  object impl {

    trait RandomNumberServiceActorFactory {

      def apply(system: ExtendedActorSystem, name: String): ActorRef
    }

    // This class would have been an ActorSystem Extension in the absence of Spring injection
    class RandomNumberServiceImpl(createSystemActor: RandomNumberServiceActorFactory,
                                  actorSystem: ExtendedActorSystem)
      extends RandomNumberService {

      override val serviceActor: ActorRef = createSystemActor(actorSystem, "random")
    }

    class RandomNumberServiceActor extends Actor with ActorLogging {

      override def preStart(): Unit = {
        super.preStart()
        log.info("Started random number generation actor.")
      }

      override def receive: Receive = {
        case GetRandomNumber =>
          sender ! RandomNumber(Random.nextDouble())
      }
    }
  }
}
