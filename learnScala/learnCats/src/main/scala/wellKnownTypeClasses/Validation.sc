import cats._
import cats.implicits._

sealed trait Validated[+A]

object Validated {
  case class Valid[+A](a:A) extends Validated[A]
  case class Invalid(errors: List[String]) extends Validated[Nothing]

  def pure[A](x:A):Validated[A] = Valid(x)

  def ap[A, B](va: Validated[A => B], vb: Validated[A]): Validated[B] =
    (va, vb) match {
      case (Valid(f), Valid(a)) => Valid(f(a))
      case (Invalid(e1), Valid(a)) => Invalid(e1)
      case (Valid(f), Invalid(e2)) => Invalid(e2)
      case (Invalid(e1), Invalid(e2)) => Invalid(e1 ++ e2)
    }

  def transformFunction[A, B, C](f: (A,B) => C)(x: A, y: B) = (x:A) => (y:B) => f(x,y)

  def x[A, B, C](f: (A,B) => C) = transformFunction(f)_

  // write map in term of validated: quite challenging
  def map[A, B, C](va: Validated[A], vb: Validated[B])(f: (A,B) => C): Validated[C] = ???

  // applicative support mapN

}


