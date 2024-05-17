
import org.apache.spark.sql.types.{DateType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.immutable.Seq


class RowDemoTest extends AnyWordSpec with BeforeAndAfterAll with Matchers {

  @transient var spark: SparkSession = _
  @transient var myDF: DataFrame = _

  override def beforeAll() = {
    spark = SparkSession.builder()
       .appName("RowDemoTest")
        .master("local[3]")
        .getOrCreate()

    val mySchema = StructType(List(
      StructField("ID", StringType),
      StructField("EventDate", StringType)))

    val myRow = Seq(Row("123", "12/12/2019"), Row("345", "13/12/2019"))
    val myRDD = spark.sparkContext.parallelize(myRow, 2)
    myDF = spark.createDataFrame(myRDD, mySchema)
  }

  override def afterAll(): Unit = {
    spark.stop()
  }

  "Test EventDate data type " should {
    "be DateType" in {
      (new RowDemo()).toDateDF(myDF, "M/d/y", "EventDate").schema.fields(1).dataType shouldBe DateType

    }
  }
}
