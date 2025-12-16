import scala.collection.immutable.Seq


ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val json4sVersion = "3.7.0-M11"

lazy val commonDependencies =  Seq("org.scalatest" %% "scalatest" % "3.1.0" % Test)

lazy val root = (project in file("."))
  .aggregate(learnCats,
             learnEffects)
  .settings(name := "learnScala")

lazy val learnScalaBasics = (project in file("learnScalaBasics"))
  .settings(name := "learnScalaBasics")

lazy val codingPractice = (project in file("codingPractice"))
  .settings(name := "codingPractice")

lazy val learnCats = (project in file("learnCats"))
  .settings(name := "learnCats", libraryDependencies += "org.typelevel" %% "cats-core" % "2.2.0")

lazy val learnEffects = (project in file("learnEffects"))
  .settings(
    name := "learnEffects",
    libraryDependencies += "org.typelevel" %% "cats-effect" % "3.1.1")

lazy val learnMagnoliaTypeClass = (project in file("learnMagnoliaTypeClass"))
  .settings(
    name := "learnMagnoliaTypeClass",
    libraryDependencies ++=
      Seq(
        "com.softwaremill.magnolia1_2" %% "magnolia" % "1.1.8",
        "org.scala-lang" % "scala-reflect" % scalaVersion.value % Provided)) //// required by magnolia


lazy val learnJson = (project in file("learnJson"))
  .settings(
      name := "learnJson",
      libraryDependencies ++= Seq(
        "org.json4s" %% "json4s-native" % "4.0.4",
        "org.json4s" %% "json4s-jackson" % "4.0.4"))

lazy val learnProtobuf = (project in file("learnProtobuf"))
  .settings(
    name := "learnProtobuf",
    libraryDependencies ++= commonDependencies ++ Seq(
      "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
      "org.json4s" %% "json4s-native" % "4.0.4"), // not needed but for comparison and creating a json serializer used
    Compile / PB.targets := Seq(
      scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"),
    Compile / PB.protocOptions += "--experimental_allow_proto3_optional")

lazy val learnChimneyProtobuf = (project in file("learnChimneyProtobuf"))
  .settings(
    name := "learnChimneyProtobuf",
    libraryDependencies ++= commonDependencies ++ Seq(
      "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
      "io.scalaland" %% "chimney" % "0.7.5"),
    Compile / PB.targets := Seq(
      scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"),
    Compile / PB.protocOptions += "--experimental_allow_proto3_optional")

lazy val scalaInterviewQuestions = (project in file("scalaInterviewQuestions"))
  .settings(
    name := "scalaInterviewQuestions")
