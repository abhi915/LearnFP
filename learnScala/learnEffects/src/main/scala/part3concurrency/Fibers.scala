package part3concurrency

import cats.effect.IOApp
import cats.effect.IO

object Fibers extends  IOApp.Simple  {

  import utils._

  val meaningOfLife = IO.pure(42)
  val favLang = IO.pure("Scala")

  def sameThreadsIO = for {
    life <- meaningOfLife.debug
    lang <- favLang.debug
  } yield ()

  // introduce Fibers

  import cats.effect.Fiber
  def createFiber: Fiber[IO, Throwable, String] = ??? // almost impossible to create fibers.

  // Fiber is a lightweight thread
  // the fiber is not actually started but fiber allocation is wrapped in another side effects.
  // allocation of fiber is effectful operation.
  val aFiber: IO[Fiber[IO, Throwable, Int]] = meaningOfLife.debug.start

  def differentThreadsIO = for {
    life <- aFiber
    lang <- favLang.debug
  } yield ()

  /*
  joining fiber: We can manage lifecycle of fiber.
   */
  import cats.effect.Outcome
  def runOnSomeOtherThread[A](io: IO[A]): IO[Outcome[IO, Throwable, A]]  = for {
    fiber <- io.start
    result <- fiber.join  // an effect: which waits fiber to terminated
  } yield result
  /*
  possible outcome
  1. Success[A]
  2.Failure[Throwable]
  3.Canceled
   */


  import scala.concurrent.duration._


  def resultFromFiber[A](a: IO[Outcome[IO, Throwable, A]]) = a.flatMap {
    case Outcome.Succeeded(ioa) => ioa
    case Outcome.Errored(e) => IO(0)
    case Outcome.Canceled() => IO(0)
  }

  def taskOnCancel = {
    val task = IO("start").debug >> IO.sleep(1.second) >> IO("end").debug

    val taskWithCancel = task.onCancel(IO("I was cancelled").debug.void)

     for {
      fiber <- task.start
      _    <- IO.sleep(500.milli) >> IO.pure("Cancelling").debug
      _    <- fiber.cancel
      result    <- fiber.join
    } yield result
  }

  override def run: IO[Unit] = resultFromFiber(taskOnCancel).debug.void

}
