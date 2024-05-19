package spark.structured.socket

import org.apache.spark.sql.SparkSession

object StreamingDataframes {

  val spark = SparkSession.builder()
    .appName("socket stream")
    .master("local[2]")
    .getOrCreate()

  def readFromSocket = {
    val lines = spark
    .readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", 12345)
      .load()

    val query = lines
      .writeStream
      .format("console")
      .outputMode("append")
      .start()

    query.awaitTermination()
  }

  def main(args: Array[String]) = {
       readFromSocket
  }

}
