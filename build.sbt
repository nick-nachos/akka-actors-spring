import Dependencies._

name := "akka-actors-spring"

version := "0.1"

scalaVersion := "2.13.7"

libraryDependencies ++= Seq(
  akkaActor,
  akkaTestKit % Test,

  mockitoScalaScalatest % Test,

  scalatest % Test,

  springCore,
  springBeans,
  springContext,
  springTest % Test
)