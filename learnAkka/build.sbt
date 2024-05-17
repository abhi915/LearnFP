ThisBuild / version := "0.1.0-SNAPSHOT"

import sbt.Keys.libraryDependencies

val akkaVersion = "2.6.18"
val scalaTestVersion = "3.2.9"
val AkkaHttpVersion = "10.5.3"


ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .aggregate(learnTypedAkkaActor)
  .settings(
    name := "learnAkkaBasics"
  )


lazy val learnTypedAkkaActor = (project in file("learnTypedAkkaActor"))
  .settings(
    name := "learnTypedAkkaActor"
  )
  .settings(
    libraryDependencies ++=
      Seq("com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "com.typesafe.akka"%% "akka-actor-testkit-typed"%akkaVersion,
      "org.scalatest" %% "scalatest" % scalaTestVersion,
        "ch.qos.logback" % "logback-classic" % "1.2.10"))

lazy val akkaStream = (project in file("akkaStream"))
  .settings(
    name := "akkaStream"
  ).settings(
    libraryDependencies ++=
      Seq("com.typesafe.akka" %% "akka-stream" % akkaVersion,
        "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion,
        "org.scalatest" %% "scalatest" % scalaTestVersion,
        "ch.qos.logback" % "logback-classic" % "1.2.10"))


lazy val akkaHttp = (project in file("akkaHttp"))
  .settings(
    name := "akkaHttp"
  ).settings(
    libraryDependencies ++=
      Seq(  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
        "com.typesafe.akka" %% "akka-stream" % akkaVersion,
        "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
         "ch.qos.logback" % "logback-classic" % "1.2.10")
  )



