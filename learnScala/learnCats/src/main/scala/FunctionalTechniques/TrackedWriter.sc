import cats._
import cats.implicits._
import cats.data._

type Tracked[A] = Writer[List[String], A]


3.pure[Tracked]
List("hello").tell

5.writer(List("hello"))

Writer(5, List("hello"))