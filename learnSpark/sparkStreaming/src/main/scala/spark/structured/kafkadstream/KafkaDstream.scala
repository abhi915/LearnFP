package spark.structured.kafkadstream

import com.fasterxml.jackson.databind.deser.std.StringDeserializer
import com.fasterxml.jackson.databind.ser.std.StringSerializer
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import spark.structured.dstream.DStreams.spark
import _root_.spark.structured.dstream.DStreams

object KafkaDstream {
  val spark = SparkSession.builder()
    .appName("kafkaDstream")
    .master("local[2]")
    .getOrCreate()

  val ssc = new StreamingContext(spark.sparkContext, Seconds(2))

  val kafkaParams: Map[String,Object] = Map("kafka.bootstrap.server"-> "localhost:9092",
    "key.serializer"-> classOf[StringSerializer],
   "value.serializer"-> classOf[StringSerializer],
  "key.deserializer"-> classOf[StringDeserializer],
  "value.deserializer"-> classOf[StringDeserializer],
  "auto.offset.reset"-> "latest",
  "enable.auto.commit"->false.asInstanceOf[Object])

  val kafkaTopic = "rockethejvm"
  def readFromKafka() = {
    val topic = Array(kafkaTopic)
    val kafkaDstream = KafkaUtils.createDirectStream(
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topic,kafkaParams + (("group.id") -> "group2") )
    )
    val processedStream = kafkaDstream.map(record => (record.key(), record.value()))
    processedStream.print()
    ssc.start()
    ssc.awaitTermination()
  }

  def main(args: Array[String]) = readFromKafka()

}
