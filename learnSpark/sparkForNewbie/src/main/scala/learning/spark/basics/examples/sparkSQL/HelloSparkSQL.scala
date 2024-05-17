package learning.spark.basics.examples.sparkSQL

import learning.spark.basics.examples.LearnSparkBasics.logger
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

import java.util.Properties
import scala.io.Source

object HelloSparkSQL extends  Serializable {
  @transient lazy val logger = Logger.getLogger("HelloSparkSQL")


  def main(args: Array[String]) = {


    if (args.length != 1) {
      logger.error("Usage: LearnSparkReadBasics <inputPath>")
      System.exit(1)
    }

    val inputPath = args(0)

    logger.info("Starting Spark Session")
    val spark = SparkSession.builder()
      .config(getSparkConf)
      .getOrCreate()


    // process your data
    val surveyDf = loadSurveyDF(spark, inputPath)
    surveyDf.createOrReplaceTempView("survey_tbl")
    val countDf = spark.sql("select Country, count(1) as count from survey_tbl where Age < 40 group by Country")


    logger.info(countDf.collect().mkString("->"))


    // stop spark session
    logger.info("Stopping Spark Session")
    spark.stop()

  }



  def loadSurveyDF(spark: SparkSession, inputPath: String) = {
    spark.read.option("header", "true")
      .option("inferSchema", "true")
      .csv(inputPath)
  }

  def getSparkConf: SparkConf = {
    val properties = new Properties()
    properties.load(Source.fromFile("sparkForNewbie/spark.properties").bufferedReader())

    val sparkConf = new SparkConf()
    properties.forEach((k,v) => sparkConf.set(k.toString, v.toString) )
    sparkConf
  }
}
