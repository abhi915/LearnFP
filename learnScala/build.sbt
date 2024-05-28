
ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"


lazy val root = (project in file("."))
  .aggregate(learnCats,
             learnEffects)
  .settings(name := "learnScala")


lazy val learnScalaBasics = (project in file("learnScalaBasics"))
  .settings(
      name := "learnScalaBasics")


lazy val learnCats = (project in file("learnCats"))
  .settings(
    name := "learnCats",
    libraryDependencies += "org.typelevel" %% "cats-core" % "2.2.0")


lazy val learnEffects = (project in file("learnEffects"))
  .settings(
    name := "learnEffects",
    libraryDependencies += "org.typelevel" %% "cats-effect" % "3.1.1")


lazy val learnMangoliaTypeClass = (project in file("learnMangoliaTypeClass"))
  .settings(
    name := "learnMangoliaTypeClass",
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

lazy val scalaInterviewQuestions = (project in file("scalaInterviewQuestions"))
  .settings(
    name := "scalaInterviewQuestions")
