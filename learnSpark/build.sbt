
ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.12"

val sparkVersion = "3.3.3"
val  log4jVersion = "2.4.1"

// assembly strategy to avoid conflict with spark
lazy val defaultMergeStrategy = assembly / assemblyMergeStrategy := {
  case PathList("META-INF", "native-image", "io.netty", _*)   => MergeStrategy.first
  case PathList("META-INF", "native", _*)                     => MergeStrategy.first
  case PathList("META-INF", "versions", _*)                   => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".kotlin_module" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".properties"    => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".proto"         => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".jnilib"        => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith "plugin.xml"     => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".jai"           => MergeStrategy.concat
  case PathList(ps @ _*) if ps.last endsWith ".jaiext"        => MergeStrategy.concat
  case "application.conf"                                     => MergeStrategy.concat
  case "module-info.class"                                    => MergeStrategy.discard
  case other: Any                                             => MergeStrategy.defaultMergeStrategy(other)
}


// No need to add log4j as we get transient dependency with spark-core
val sparkDependencies = Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
)

val testDependencies = Seq(
  "org.scalatest" %% "scalatest" % "3.1.0" % Test
)

lazy val root = (project in file("."))
  .aggregate(learnSparkBasics,
    sparkRDD,
    sparkHiveExample)
  .settings(name := "learnSpark")


lazy val learnSparkBasics = (project in file("sparkForNewbie"))
  .settings(
    name := "sparkForNewbie",
    libraryDependencies ++= sparkDependencies ++ testDependencies
  )

lazy val sparkRDD = (project in file("sparkRDD"))
  .settings(
    name := "sparkRDD",
    libraryDependencies ++= Seq("org.apache.spark" %% "spark-core" % sparkVersion) ++ testDependencies
  )

lazy val sparkHiveExample = (project in file("sparkHiveExample"))
  .settings(
    name := "sparkHiveExample",
    libraryDependencies ++= sparkDependencies ++
      Seq("org.apache.spark" %% "spark-hive" % sparkVersion) ++ testDependencies
  )

lazy val sparkStreaming = (project in file("sparkStreaming"))
  .settings(
    name := "sparkStreaming",
    libraryDependencies ++= sparkDependencies ++ Seq("org.apache.spark" %% "spark-streaming" % sparkVersion,
      "org.apache.spark" % "spark-sql-kafka-0-10_2.12" % sparkVersion,   // spark kafka
      "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion,// spark kafka low level integration

      "org.apache.logging.log4j" % "log4j-api" % log4jVersion,  // logging
      "org.apache.logging.log4j" % "log4j-core" % log4jVersion) ++ testDependencies
  )