package learning.spark.basics.examples.schemaDemo

import learning.spark.basics.examples.schemaDemo.SchemaCSVDemo.{getSparkConf, logger}
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import java.util.Properties
import scala.io.Source

object SchemaJSONDemo {

  @transient lazy val logger = Logger.getLogger("SchemaJSONDemo")

  def main(args: Array[String]) = {
    val spark = SparkSession.builder()
      .config(getSparkConf)
      .getOrCreate()

    val flightTimeJSONDF = spark.read
      .format("json")
      .option("header", "true")
      .load("sparkForNewbie/data/flight*.json")

    flightTimeJSONDF.show(5)

    logger.info("schema>>" + flightTimeJSONDF.schema.toString())

    spark.close()
  }

  def getSparkConf: SparkConf = {
    val properties = new Properties()
    properties.load(Source.fromFile("sparkForNewbie/spark.properties").bufferedReader())

    val sparkConf = new SparkConf()
    properties.forEach((k,v) => sparkConf.set(k.toString, v.toString))
    sparkConf
  }
}
