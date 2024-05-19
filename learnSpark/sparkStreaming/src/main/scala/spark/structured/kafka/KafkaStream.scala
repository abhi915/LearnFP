package spark.structured.kafka

import org.apache.spark.sql.SparkSession

object KafkaStream {

  val spark = SparkSession.builder()
    .appName("kafka")
    .master("local[2]")
    .getOrCreate()


  def readFromKafka() = {
   val kafkaDF =  spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers","localhost:9092")
      .option("subscribe", "rockthejvm")
      .load()

    kafkaDF
      .writeStream
      .format("console")
      .outputMode("append")
      .start()
      .awaitTermination()
  }

  def main(args: Array[String]) = readFromKafka()

}
