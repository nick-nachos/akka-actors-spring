package net.nachos.actors.di

import akka.actor.{Actor, ActorLogging, Timers}
import net.nachos.actors.di.ChildActor.{RandomQueryTick, RandomQueryTimerKey}

import scala.concurrent.duration._

object ChildActor {

  private val RandomQueryTimerKey = "RandomQueryTimer"

  private case object RandomQueryTick
}

class ChildActor(randomNumberService: RandomNumberService)
                (randomQueryInterval: FiniteDuration = 10.seconds)
  extends Actor with ActorLogging with Timers {

  override def preStart(): Unit = {
    super.preStart()
    log.info("Starting child")
    setTimer()
  }

  override def postStop(): Unit = {
    log.info("Stopping child")
    timers.cancelAll()
    super.postStop()
  }

  override def receive: Receive = {
    case RandomQueryTick =>
      log.info("Asking for a random number from the system service")
      randomNumberService.serviceActor ! RandomNumberService.GetRandomNumber

    case RandomNumberService.RandomNumber(num) =>
      log.info("Got random number {} from the system service", num)
      setTimer()
  }

  private def setTimer(): Unit = {
    log.info("Will be asking for a random number from the system service in {}", randomQueryInterval)
    timers.startSingleTimer(RandomQueryTimerKey, RandomQueryTick, randomQueryInterval)
  }
}
