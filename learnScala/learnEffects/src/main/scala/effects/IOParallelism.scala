package effects

import cats.effect.IO
import cats.effect.IOApp

object IOParallelism extends  IOApp.Simple  {


  val aniIO1 = IO(println(s"Hello from ${Thread.currentThread().getName} ani"))
  val kamran1 = IO(println(s"Hello from ${Thread.currentThread().getName} kamran"))

  val aniIO = IO(s"Hello from ${Thread.currentThread().getName} ani")
  val kamran = IO(s"Hello from ${Thread.currentThread().getName} kamran")

  val composedIO = for {
    ani <- aniIO
    kamran <- kamran
  } yield s"$ani $kamran running."

  // debug extension method
  import utils._

  // mapN extension method
  import cats.syntax.apply._

  val meaningOfLife = IO(42)
  val favLang = IO("Scala")

  val goInLife = (meaningOfLife.debug, favLang.debug).mapN((life, lang) => s"Meaning of life is $life and my fav lang is $lang with ${Thread.currentThread().getName}")


  // wrapp IO in parallel.
  import cats.Parallel
  val parIO1: IO.Par[Int] = Parallel[IO].parallel(meaningOfLife.debug)
  val parIO2: IO.Par[String] = Parallel[IO].parallel(favLang.debug)

  import cats.effect.implicits._
  val goInLifeParallel = (parIO1, parIO2).mapN((life, lang) => s"Meaning of life is $life and my fav lang is $lang with ${Thread.currentThread().getName}")

  // make it sequentail
  val goInLife_v2 = Parallel[IO].sequential(goInLifeParallel)


  // shortHand with implicits....
  import cats.syntax.parallel._
  val goInLife_v3 = (meaningOfLife.debug, favLang.debug).parMapN((life, lang) => s"Meaning of life is $life and my fav lang is $lang with ${Thread.currentThread().getName}")

  // regarding failure
  val aFailure: IO[String]= IO.raiseError(new RuntimeException("Boom!"))

  // compose success and failure
  val parallelWithFailure = (favLang.debug, aFailure.debug).parMapN(_ + _)

  val anotherFailure: IO[String] = IO.raiseError(new RuntimeException("Boom2!"))

  // first effect to fail gives failure of result
  val twoFailure = (aFailure.debug, anotherFailure.debug).parMapN(_ + _)

  def run = twoFailure.map(println)

}
