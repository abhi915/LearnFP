package learning.spark.basics.examples.schemaDemo

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession


import org.apache.log4j.Logger
import java.util.Properties
import scala.io.Source

object SchemaCSVDemo extends Serializable {

  @transient lazy val logger = Logger.getLogger("SchemaCSVDemo")

  def main(arg: Array[String]) = {

   val spark = SparkSession.builder()
     .config(getSparkConf)
     .getOrCreate()

    val flightTimeCSVDF = spark.read
      .format("csv")
      .option("header", "true")
      .load("sparkForNewbie/data/flight*.csv")

    flightTimeCSVDF.show(5)

    logger.info("schema>>" + flightTimeCSVDF.schema.toString())

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
