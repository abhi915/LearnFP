package learning.spark.rdd.examples

import org.apache.log4j.Logger
import org.apache.spark.{SparkConf, SparkContext}

import java.util.Properties
import scala.io.Source


/*
Datafram API (1.3)
- Generic Type api
- Expression based transformation such as
    - select, where, join, groupBy, orderBy, agg, dropDuplicates etc


RDD give flexibility to use function based transformation. Dataframe api is more optimized than RDD api,
but not give flexibility to use function based transformation. Thus Dataset api is introduced.


Dataset API (1.6)
Strongly typed api.
function based transformation.


RDD (0.x)
Strongly typed api.
RDD supports function based transformation
     - map, filter, reduce, foreach



---------------------
spark 2.0 ---> dataframe and dataset api combined in spark structured api.

Dataframe API ---> Dataset[Row] (Row is a generic type)
                          |
                          |      spark implicit conversion (encoder)
                          |
Dataset API ---> Dataset[UserDefinedType]

 */

case class SurveryRecord(age: Int, Gender: String, Country: String, state: String)

object sparkRDD extends Serializable {

  @transient lazy val logger = Logger.getLogger("sparkRDD")



  def main(args: Array[String]) = {


    if (args.length != 1) {
      logger.error("Usage: LearnSparkReadBasics <inputPath>")
      System.exit(1)
    }

    // To us RDD api.
    val sparkContext = new SparkContext(getSparkConf)

    val linesRDD = sparkContext.textFile(args(0))

    val partitionedRDD = linesRDD.repartition(2)

    val colsRDD = partitionedRDD.map(line => line.split(",").map(_.trim))

    val selectRDD = colsRDD.map(cols => SurveryRecord(cols(1).toInt, cols(2), cols(3), cols(4)))

    val filterRDD = selectRDD.filter( _.age < 40)

    val kvRDD = filterRDD.map(row => (row.Country, 1))

    val countRDD  = kvRDD.reduceByKey((v1, v2) =>v1 + v2)

    logger.info("---------------------------------\n" +
                countRDD.collect().mkString("->") +
                "\n---------------------------------")

    sparkContext.stop()


  }

  def getSparkConf: SparkConf = {
    val properties = new Properties()
    properties.load(Source.fromFile("sparkRDD/spark.properties").bufferedReader())

    val sparkConf = new SparkConf()
    properties.forEach((k,v) => sparkConf.set(k.toString, v.toString) )
    sparkConf
  }
}
