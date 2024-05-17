import org.apache.spark.sql.DataFrame

import org.apache.spark.sql.functions._

class RowDemo extends  Serializable {

  def toDateDF(dataFrame: DataFrame, fmt: String, fld: String)= dataFrame.withColumn(fld, to_date(col(fld), fmt))

}
