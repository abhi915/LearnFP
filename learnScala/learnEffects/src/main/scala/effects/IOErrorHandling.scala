package effects

import cats.effect.IO
import scala.util.{Failure, Success, Try}

object IOErrorHandling {

  // IO: pure, delay, defer

  val failureCompute = IO.delay(throw new RuntimeException("Boom!"))
  val failure: IO[Int] = IO.raiseError(new RuntimeException("Proper Fail!"))


  // handle Exception
  val handledFailure = failure.handleErrorWith {
    case _: RuntimeException => IO.delay(println("I am still here!!!"))
  }

  // turn IO into Either
  val effectAsEither: IO[Either[Throwable, Int]] = failure.attempt


 // transform failue into success
  val effectRedeem = failure.redeem(
    throwable => s"Got an error: $throwable",
    value => s"Got a valid value: $value"
  )

  // redeemWith
  val effectRedeemWith = failure.redeemWith(
    throwable => IO.delay(println(s"Got an error: $throwable")),
    value => IO.delay(println(s"Got a valid value: $value"))
  )

  /*
   *Exercise
   *
   */

  def option2IO[A](option: Option[A])(ifEmpty: => Throwable): IO[A] = {
    option match {
      case Some(value) => IO(value)
      case None => IO.raiseError(ifEmpty)
    }
  }

  def try2IO[A](aTry: Try[A]): IO[A] = {
    aTry match {
      case Success(value) => IO(value)
      case Failure(exception) => IO.raiseError(exception)
    }
  }

  def Either2IO[A](either: Either[Throwable, A]): IO[A] = {
    either match {
      case Right(value) => IO(value)
      case Left(exception) => IO.raiseError(exception)
    }
  }

  def handleError[A](io: IO[A])(handler: Throwable => A): IO[A] =  io.redeem(handler, identity)

  def main(args: Array[String]): Unit = {


    import cats.effect.unsafe.implicits.global

    failure.unsafeRunSync()



  }

}
