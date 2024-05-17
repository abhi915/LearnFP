package learning.spark.basics.examples.schemaDemo

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import java.util.Properties
import scala.io.Source

object SchemaParquetDemo extends Serializable{
  @transient lazy val logger = Logger.getLogger("SchemaParquetDemo")

  def main(args: Array[String]) = {
    val spark = SparkSession.builder()
      .config(getSparkConf)
      .getOrCreate()

    val flightTimeParqDF = spark.read
      .format("parquet")
      .option("header", "true")
      .load("sparkForNewbie/data/flight*.parquet")

    flightTimeParqDF.show(5)

    logger.info("schema>>" + flightTimeParqDF.schema.toString())

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
