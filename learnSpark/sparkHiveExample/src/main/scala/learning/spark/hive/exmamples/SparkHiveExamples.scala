package learning.spark.hive.exmamples

import org.apache.log4j.Logger
import org.apache.spark.sql.{SaveMode, SparkSession}


object SparkHiveExamples extends Serializable {
  @transient lazy val logger  = Logger.getLogger("SparkHiveExamples")

  def main(args: Array[String]) = {

    val spark = SparkSession
      .builder()
      .appName("Spark Hive Example")
      .master("local[3]")
      .config("spark.sql.warehouse.dir", "sparkHiveExample/warehouse/")
      .config("hive.metastore.warehouse.dir", "sparkHiveExample/metastore/")
      .enableHiveSupport()
      .getOrCreate()


    val flightTimeParquetDF = spark
      .read
      .format("parquet")
      .option("path", "sparkHiveExample/data/flight*.parquet")
      .load()




    spark.sql("CREATE DATABASE IF NOT EXISTS AIRLINE_DB")


    spark.catalog.setCurrentDatabase("AIRLINE_DB")

    flightTimeParquetDF.write
      .mode(SaveMode.Overwrite)
      .bucketBy(5, "OP_CARRIER", "ORIGIN")  // hash based partition
      .sortBy("OP_CARRIER", "ORIGIN")
      .saveAsTable("flight_data_tbl")

    logger.info("Finished...")

    spark.catalog.listTables("AIRLINE_DB").show()
    spark.stop()

  }


}
