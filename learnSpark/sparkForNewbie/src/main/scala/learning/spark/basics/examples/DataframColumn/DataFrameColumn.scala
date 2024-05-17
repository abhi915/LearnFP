package learning.spark.basics.examples.DataframColumn

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}

import java.util.Properties
import scala.io.Source

/*
Two ways to refere column in dataframe transformation
1. using column name
2. using column object

 */


object DataFrameColumn extends Serializable {
  @transient lazy val logger = Logger.getLogger("DataFrameColumn")

  def main(args: Array[String]) = {
    val spark = SparkSession.builder()
      .config(getSparkConf)
      .getOrCreate()

    val flightTimeParqDF = spark.read
      .format("parquet")
      .option("header", "true")
      .load("sparkForNewbie/data/flight*.parquet")

    val partitionData = flightTimeParqDF.repartition(5)

    partitionData.select("OP_CARRIER", "ORIGIN", "DEST", "CRS_DEP_TIME", "DISTANCE").show(5)

    import org.apache.spark.sql.functions._
    import spark.implicits._

    partitionData.select(column("OP_CARRIER"), col("ORIGIN"), $"DEST", 'DISTANCE).show(5)









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
