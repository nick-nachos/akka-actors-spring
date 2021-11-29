package net.nachos.actors.di.app

import net.nachos.actors.di.ActorApp
import net.nachos.actors.di.beans.ActorAppConfiguration
import org.springframework.context.annotation.AnnotationConfigApplicationContext

import scala.concurrent.duration._

object Main extends App {

  val appContext = new AnnotationConfigApplicationContext(classOf[ActorAppConfiguration])
  val appObj = appContext.getBean(classOf[ActorApp])
  appObj.start()
  Thread.sleep(1.minute.toMillis)
  appObj.stop()
}
