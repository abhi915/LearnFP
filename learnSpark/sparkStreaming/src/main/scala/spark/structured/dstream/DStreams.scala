package spark.structured.dstream

import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{StreamingContext, Seconds}
import org.apache.spark.streaming.dstream.DStream

object DStreams {

  val spark =
    SparkSession.builder()
      .appName("dstream learn")
      .master("local[2]")
      .getOrCreate()
/*
spark streaming context: entry point to the Dstream API
- need the spark context
- a duration: batch interval
 */
  val ssc = new StreamingContext(spark.sparkContext, Seconds(2))



  /*
  - Define input sources by creating dstream
  - define transfomation on dstream
  - start all computation with ssc.start
      - no more compuation can be added.
  - await termination or stop the computation


distributed never ending collection of streams.
   */

  def readFromSocket()={
    val socketStream: DStream[String] = ssc.socketTextStream("localhost",12345)
    // transfomation
    val wordStream = socketStream.flatMap(line => line.split(" "))
    // action
    wordStream.print()

    ssc.start()
    ssc.awaitTermination()
  }

  def main(args: Array[String]): Unit = {
    readFromSocket()
  }

}
