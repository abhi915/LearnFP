ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .settings(
    name := "learnFS2"
  )

lazy val learnFS2Basics = project
  .settings(
    name := "learnFS2Basics",
    libraryDependencies ++= Seq(
      "co.fs2" %% "fs2-core" % "3.2.7",
      "co.fs2" %% "fs2-io" % "3.2.7"
    )
  )
