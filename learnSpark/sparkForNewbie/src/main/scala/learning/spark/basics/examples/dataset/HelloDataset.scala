package learning.spark.basics.examples.dataset

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame

// When dataframe is converted to dataset, serialization happens.
/*
Dataframe = Dataset[Row] => managed by tungsten serializer     --> It's bit faster than java serializer
Dataset[UserDefinedObject] => managed by java serializer
 */


import java.util.Properties
import scala.io.Source

case class SurveryRecord(age: Int, Gender: String, Country: String, state: String)

object HelloDataset extends Serializable {

  @transient lazy val logger  = Logger.getLogger("HelloDataset")

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


    import spark.implicits._

    // process your data
    val surveyDF: DataFrame = loadSurveyDF(spark, inputPath)

    val surveRecord = surveyDF.select("Age", "Gender", "Country", "state").as[SurveryRecord]


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
