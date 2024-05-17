package effectful

import cats.effect.IO
import cats.effect.IOApp
import fs2.Stream




import scala.concurrent.duration.FiniteDuration

object Create extends IOApp.Simple {


   override def run: IO[Unit] = {
   val s = Stream.eval(IO(println("Hello, world!")))
    val compileStream = s.compile
    compileStream.drain


    val s2 : Stream[IO, Nothing]= Stream.exec(IO(println("Hello, world!")))
    s2.compile.drain

    val data = List.range(1,100)
    val pageSize = 20

    def fetchPage(pageNumber: Int): IO[List[Int]] = {
      val start = pageNumber*pageSize
      val end = start + pageSize
      IO.println("Fetching page " + pageNumber) >> IO.pure(data.slice(start, end))
    }


    def fetchAll(): Stream[IO, Int] = {
      Stream.range(0, data.length / pageSize + 1).covary[IO].evalMap(fetchPage).flatMap(Stream.emits)
    }
   // fetchAll().compile.toList.map(println)


     val s5 = Stream.exec(IO(println("start"))) ++ Stream.emits(List(1,2,3)) ++ Stream.emits(List(4,5,6)) ++ Stream.exec(IO(println("end")))
     s5.compile.toList.flatMap(IO.println)

     def evalEvery[A](d: FiniteDuration, fa: IO[A]) : Stream[IO, A] = {
       (Stream.eval(fa)).repeat
     }

     evalEvery(FiniteDuration(1, "seconds"), IO(println("Hello, world!"))).take(5).compile.drain

  }
}
