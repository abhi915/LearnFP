package learning.spark.basics.examples.DataFrameWriter

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}

import java.util.Properties
import scala.io.Source

/*
  Dataframe.write
       .format("csv")
       .mode(saveMode)
       .option("path", "sparkForNewbie/output/flightTimeCSVDF")
       .save()

       saveMode -> SaveMode.Append -> create New file
                   SaveMode.Overwrite -> overwrite existing file
                   SaveMode.ErrorIfExists -> throw error if file exists
                   SaveMode.Ignore -> ignore if file exists
       , SaveMode.Overwrite, SaveMode.ErrorIfExists, SaveMode.Ignore
        format -> csv, json, parquet, orc, jdbc, text, etc
        option -> path, header, delimiter, etc


Layout of output data
  1. Repartition
        1.1. partitionBy  - key based partition
        1.2. repartition - blind partiotion
        1.3. bucketBy - bucket based partition (hash based partition)

   2. Sort
        2.1. sortBy - sort by column name
        2.2. maxRecordsPerFile - number of records per file

  partitionBy -> key based partition
  repartion(n) -> n is number of partition
  bucketBy(n, "colName") -> n is number of bucket


  sortBy("colName") -> sort by column name
  maxRecordsPerFile(n) -> n is number of records per file

 */

object DataFrameWriter extends Serializable {
  @transient lazy val logger = Logger.getLogger("SchemaParquetDemo")

  def main(args: Array[String]) = {
    val spark = SparkSession.builder()
      .config(getSparkConf)
      .getOrCreate()

    val flightTimeParqDF = spark.read
      .format("parquet")
      .option("header", "true")
      .load("sparkForNewbie/data/flight*.parquet")

    val partitionData = flightTimeParqDF.repartition(5)

    logger.info("Number of partion>>" + partitionData.rdd.getNumPartitions)
    import org.apache.spark.sql.functions.spark_partition_id
    partitionData.groupBy(spark_partition_id()).count().show(5)

    partitionData.write
      .format("json")
      .mode(saveMode = SaveMode.Overwrite)  // clean the directory and write the data
      .option("path", "sparkForNewbie/output/flightTimeJsonDF")
      .option("maxRecordsPerFile", 10000) // max no of records per file
      .partitionBy("OP_CARRIER", "ORIGIN")
      .save()

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
