package learning.spark.basics.examples

import org.apache.spark.sql.SparkSession
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatest.matchers.should.Matchers


class LearnSparkBasicsTest extends AnyWordSpecLike with Matchers with BeforeAndAfterAll  {


  @transient var spark: SparkSession = _

  override def beforeAll() = {
    spark = SparkSession.builder()
      .appName("Learn Spark Basics Test")
      .master("local[3]")
      .getOrCreate()
  }

  override def afterAll() = {
    spark.stop()
  }

  "data file loaded from sample data" should {
    "have 9 columns" in {
      val surveyDF = LearnSparkReadBasics.loadSurveyDF(spark, "sparkForNewbie/data/sample.csv")
      assert(surveyDF.count() == 9)
    }

    "test countDF" in {

      val surveyDF = LearnSparkReadBasics.loadSurveyDF(spark, "sparkForNewbie/data/sample.csv")
      val countByCountry = LearnSparkReadBasics.countDF(spark, surveyDF)
      val mockData = Map("United States" -> 4, "Canada" -> 2, "United Kingdom" -> 1)

      val countByCountryMap = countByCountry.collect().map(row => (row.getString(0), row.getLong(1))).toMap
      countByCountryMap should contain theSameElementsAs mockData
      countByCountryMap("United States") should be(4)
      countByCountryMap("Canada") should be(2)
      countByCountryMap("United Kingdom") should be(1)
    }

  }
}