import sbt._

object Dependencies {

  private object akka {
    val organization = "com.typesafe.akka"
    val version = "2.6.17"
  }

  private object spring {
    val organization = "org.springframework"
    val version = "5.3.13"
  }

  val akkaActor = akka.organization %% "akka-actor" % akka.version
  val akkaTestKit = akka.organization %% "akka-testkit" % akka.version

  val mockitoScalaScalatest = "org.mockito" %% "mockito-scala-scalatest" % "1.16.46"

  val scalatest = "org.scalatest" %% "scalatest" % "3.2.10"

  val springCore = spring.organization % "spring-core" % spring.version
  val springBeans = spring.organization % "spring-beans" % spring.version
  val springContext = spring.organization % "spring-context" % spring.version
  val springTest = spring.organization % "spring-test" % spring.version
}
