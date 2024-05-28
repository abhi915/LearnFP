import cats._
import cats.implicits._


// Functor: support map function ---> we can transform A -> B, F[A] -> F[B]


import scala.collection.immutable._
import scala.annotation.tailrec

class Secret[A](val name: A) {

  def hash = name.hashCode

  override def toString: String = name.toString

}

object Secret {
  def map[A, B](s: Secret[A])(f: A => B): Secret[B] = new Secret(f(s.name))
}



// Option Functor

val optionFunctor = new Functor[Option] {
  override def map[A, B](fa: Option[A])(f: A => B): Option[B] = {
        fa match {
          case None => None
          case Some(value) => Some(f(value))
        }
  }
}

val listFunctor = new Functor[List] {
  override def map[A, B](fa: List[A])(f: A => B): List[B] = {
    @tailrec
    def transform(list: List[A], transformList: List[B]): List[B] = {
          list match {
            case Nil => transformList
            case head :: tail =>   transform(tail, transformList :+ f(head))
          }
    }
    transform(fa, List.empty[B])
  }
}