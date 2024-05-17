package learning.spark.basics.examples

import learning.spark.basics.examples.LearnSparkBasics.{getSparkConf, logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.api.java.UDF1
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf
import org.apache.spark.sql.types.{IntegerType, StructType}
import org.apache.spark.sql.{DataFrame, SparkSession}

import java.util.Properties
import scala.io.Source


/*
Spark has
1. DataFrames
    - DataFrames are immutable distributed collection of data in rows and columns format.
    - We can visualize it as database table.
    - DataFrames are built on top of RDDs.
    - Dataframe schema has column names, column types and metadata.
2. Datasets
3. RDDs

 */

/*
   Spark dataframes undergoes
      1. transformation
      2. action

    Transformation:
      - Transformation is a function that produces new dataframe from existing dataframe.
      - Transformation is a lazy operation.
      - Transformation is not executed until an action is performed.
      - Examples: select(), filter(), groupBy(), orderBy(), dropDuplicates() etc
      1. Narrow dependency
          - tranformation does not required data from other partition to create valid result.
          - Each partition of parent RDD is used by at most one partition of child RDD.
          - Example: map(), filter(), union(), join() etc
      2. Wide dependency
          - transformation required data from other partitions to create valid result.
          - Example: groupBy(), distinct(), sortByKey() etc


Action -  Read, Write, collect, count, show, take, reduce, foreach etc


 */

/*
   spark execution devided into
       jobs -> stages -> tasks
        1. job - spark action triggers a job.
 */

/*
How to debug spark applicaiton?
1. why my breakpoint are stopping in driver?
2. How to debug executor?
 */
object LearnSparkReadBasics extends Serializable {
  case class AgeADD10And100(ageAdd10: Int, ageAdd100: Int)
  val ageADD10And100Type = new StructType()
    .add("add10", IntegerType)
    .add("add100", IntegerType)
  val ageADD10And100: UserDefinedFunction =
    udf[AgeADD10And100, Int]((age: Int) => {
      AgeADD10And100(age + 10, age + 100)
    })
  val ageADD10And100Struct = udf(new UDF1[Int, AgeADD10And100] {
    override def call(age: Int): AgeADD10And100 = AgeADD10And100(age + 10, age + 100)
  },ageADD10And100Type)

  def main(args: Array[String]) = {
    val inputPath = args(0)
    logger.info("Starting Spark Session")
    val spark = SparkSession.builder()
      .config(getSparkConf)
      .getOrCreate()
    // process your data
    val surveyDF = spark.read.option("header", "true")
      .option("inferSchema", "true")
      .csv(inputPath)
    val partitionSurveyDF = surveyDF.repartition(2) // to simulate real world scenario - create 2 partition
    val countByCountry = partitionSurveyDF.where("Age < 40")
      .select("Age", "Gender", "Country", "state")
    import spark.implicits._
    val partitionDFTwoWithUDF1 = countByCountry.withColumn("hello", ageADD10And100('Age))
    val partitionDFTwoWithUDF2 = countByCountry.withColumn("hello", ageADD10And100Struct('Age))
    val countDf = countByCountry.groupBy("Country").count()
    partitionDFTwoWithUDF1.explain("codegen")
    partitionDFTwoWithUDF2.explain("codegen")
    countByCountry.explain("codegen")

    logger.info("Stopping Spark Session")
    scala.io.StdIn.readLine()
    spark.stop()

  }

  def getSparkConf: SparkConf = {
    val properties = new Properties()
    properties.load(Source.fromFile("sparkForNewbie/spark.properties").bufferedReader())

    val sparkConf = new SparkConf()
    properties.forEach((k,v) => sparkConf.set(k.toString, v.toString) )
    sparkConf
  }

}
