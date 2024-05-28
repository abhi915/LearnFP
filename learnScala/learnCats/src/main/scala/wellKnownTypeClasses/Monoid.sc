

/*
Monoid: support min operation combine and empty.


value2 + value2 ==> shoule combine 2 values
empty + value ==> value
value + empty ==> value

 */

import cats._
import cats.implicits._

case class Speed(value: Long)

object Speed {

  def addSpeed(x: Speed, y: Speed): Speed = Speed(x.value+y.value)

  implicit val speedMonoid: Monoid[Speed] =  Monoid.instance(Speed(0), addSpeed)

}