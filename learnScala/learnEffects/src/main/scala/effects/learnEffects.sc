import cats.Parallel
import cats.effect.IO
import cats.effect.unsafe.implicits.global

def sequenceTakeLast[A,B](ioa: IO[A], iob: IO[B]): IO[B] =
  ioa.flatMap(_ => iob)

def sequenceTakeFirst[A, B](ioa: IO[A], iob:IO[B]): IO[A] =
  ioa.flatMap(a => iob.map(_ => a))

def forever[A](ioa: IO[A]): IO[A] =
  ioa.flatMap(_ => forever(ioa))

def convert[A, B](ioa: IO[A], value: B): IO[B] =
  ioa.map(_ => value)

def asUnit[A](ioa: IO[A]): IO[Unit] =
  ioa.map(_ => ())

def sum(n: Int): Int = {
  if (n <= 0) 0
  else n + sum(n - 1)
}

def sumIO(n: Int): IO[Int] = {
  if (n <= 0) IO.pure(0)
  else sumIO(n - 1).map(_ + n)
}

def sumIO2(n: Int): IO[Int] = {
  def loop(n: Int, acc: Int): IO[Int] = {
    if (n <= 0) IO.pure(acc)
    else loop(n - 1, acc + n)
  }
  loop(n, 0)
}

// 1,1,2,3,5,8,13,21,34,55
def fibnocci(n: Int): IO[BigInt] = {
  if (n <= 0) IO.pure(0)
  else if (n == 1) IO.pure(1)
  else fibnocci(n - 1).flatMap(a => fibnocci(n - 2).map(b => a + b))
}

import cats.syntax.parallel
import cats.Parallel
import cats.effect.implicits._
import scala.concurrent.ExecutionContext.Implicits.global
val data = Parallel[Option].parallel(Some(s"hello ${Thread.currentThread()}"))
val data2 = Parallel[IO].parallel(IO(s"hello on another thread ${Thread.currentThread()}"))

import cats.syntax.apply._
import cats.effect.implicits._
val runParalled = (data, data2).mapN( (_,_) => "${Thread.currentThread()}")

val runSequential = Parallel[IO].sequential(runParalled).unsafeRunSync()




