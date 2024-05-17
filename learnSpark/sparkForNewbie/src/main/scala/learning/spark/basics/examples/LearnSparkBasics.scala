package learning.spark.basics.examples

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import java.util.Properties
import scala.io.Source

/*
How spark-submit works?
1. spark-submit is a script that is used to submit a Spark job to the cluster.
2. spark-submit will merge configs to SparkConf.
3. SparkConf takes the highest precedence.
 */

/*
When to use spark-submit and spark-conf?
1. Deployment configs should be passed via spark-submit.
      -- spark.driver.memory, spark.executor.instances, spark.executor.memory, spark.executor.cores etc
2. Application configs should be passed via SparkConf.
      -- spark.app.name, spark.master, spark.sql.shuffle.partitions etc

 Precednces of spark configs:
   1. SparkConf
   2. spark-submit
   3. spark-defaults.conf
   4. spark-env.sh
 */

object LearnSparkBasics extends Serializable {

  @transient lazy val logger = Logger.getLogger("LearnSparkBasics")
// each application has one and only one sprakSession.
// sparkSession is the entry point to programming Spark with the Dataset and DataFrame API.

  def main(args: Array[String]) = {

    logger.info("Starting Spark Session")
    /*
      val spark = SparkSession.builder()
        .appName("Learn Spark Basics")
        .master("local[3]")
        .getOrCreate()
   */
    val spark = SparkSession.builder()
      .config(getSparkConf)
      .getOrCreate()

    // process your data




    // stop spark session
    logger.info("Stopping Spark Session")
    spark.stop()
  }

  def getSparkConf: SparkConf = {
    val properties = new Properties()
    properties.load(Source.fromFile("sparkForNewbie/spark.properties").bufferedReader())

    val sparkConf = new SparkConf()
    properties.forEach( (k,v) => sparkConf.set(k.toString, v.toString) )
    sparkConf
  }

}
