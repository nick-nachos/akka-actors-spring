name := "akka-actors-spring"

version := "0.1"

scalaVersion := "2.13.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.6.17",
  "com.typesafe.akka" %% "akka-testkit" % "2.6.17" % Test,

  "org.springframework" % "spring-core" % "5.3.13",
  "org.springframework" % "spring-beans" % "5.3.13",
  "org.springframework" % "spring-context" % "5.3.13",
  "org.springframework" % "spring-test" % "5.3.13" % Test

)