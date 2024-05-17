import magnolia1._
import scala.language.experimental.macros

trait Show[T] {
  def show(value: T): String
}

import language.experimental.macros
import magnolia1._

trait ShowDerivation {
  type Typeclass[T] = Show[T]

  def join[T](ctx: CaseClass[Show, T]): Show[T] = new Show[T] {
    def show(value: T): String = ctx.parameters.map { p =>
      s"${p.label}=${p.typeclass.show(p.dereference(value))}"
    }.mkString("{", ",", "}")
  }

  def split[T](ctx: SealedTrait[Show, T]): Show[T] =
    new Show[T] {
      def show(value: T): String = ctx.split(value) { sub =>
        sub.typeclass.show(sub.cast(value))
      }
    }

  implicit def gen[T]: Show[T] = macro Magnolia.gen[T]
}

object ShowDerivation extends ShowDerivation  {

  implicit val dataString: Show[String] = identity(_)
  implicit val dataInt: Show[Int] = _.toString
  def apply[T:Show]: Show[T] = implicitly[Show[T]]
}



case class Working(bar: Int, hello: String)

sealed trait Tree[+T]
case class Branch[+T](left: Tree[T], right: Tree[T]) extends Tree[T]
case class Leaf[+T](value: T) extends Tree[T]



def main(): Unit = {
  import ShowDerivation.gen
  import ShowDerivation._


  println(ShowDerivation[Working].show(Working(89, "Abhi")))

 // println(ShowDerivation.gen[Branch[Int]].show(Branch(Branch(Leaf(1), Leaf(2)), Leaf(3))))

}


main()