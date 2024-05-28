package effects

import cats.effect.IO
import cats.effect.IOApp

import scala.util.Random

object IOTraversal extends IOApp.Simple {


  import scala.concurrent.Future
  import scala.concurrent.ExecutionContext.Implicits.global

  def workLoad(data: String): Future[Int]  = Future {
    Thread.sleep(Random.nextInt(1000))
    data.split(" ").length
  }

  import utils._

  def workLoadIO(data: String) : IO[Int] = IO {
    Thread.sleep(Random.nextInt(1000))
    data.split(" ").length
  }.debug

  val data: List[String] = List("Hello World", "learning scala and ", "cats effect is fun.", "I am enjoying it.")


  import cats.Traverse

  val traversList: Future[List[Int]] = Traverse[List].traverse(data)(workLoad)

  val traversListIO: IO[List[Int]] = Traverse[List].traverse(data)(workLoadIO)


  // parallel traversal
  import cats.syntax.parallel._
  val parallelTraversListIO: IO[List[Int]] = data.parTraverse(workLoadIO)

  def sequence[A](listOfIOs: List[IO[A]]): IO[List[A]] = IO {
    import cats.effect.unsafe.implicits.global
    listOfIOs.map(_.unsafeRunSync())
  }

  def sequence1[A](listOfIOs: List[IO[A]]): IO[List[A]] = Traverse[List].traverse(listOfIOs)(identity)


  import cats.implicits._
  def sequence[F[_]: Traverse, A](listOf: F[IO[A]]) : IO[F[A]] = IO {
    import cats.effect.unsafe.implicits.global
    listOf.map(_.unsafeRunSync())
  }


  override  def run: IO[Unit] = parallelTraversListIO.void

}
