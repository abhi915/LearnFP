package part3concurrency


import cats.effect.IOApp
import cats.effect.IO
import scala.io.Source
import scala.io.BufferedSource
import java.lang.Thread

/*
use case: manage connection lifecycle


 */
object Resources extends IOApp.Simple {

import utils._

  class Connection(url: String) {
    def open(): IO[String] = IO(s"opening connection ${url}").debug
    def close(): IO[String] = IO(s"closing connection ${url}").debug
    def doStuff(): IO[String] = IO(s"working with connection ${url}").debug
  }

  // bracket pattern:
import scala.concurrent.duration._
  val bracketFetchUrl = IO(new Connection("while learning bracket")).bracket(
    conn => conn.open() >> IO.sleep(Int.MaxValue.second)
  )(conn => conn.close().void)

  val bracketProgram = for {
    fib <- bracketFetchUrl.start
    _ <- IO.sleep(1.second) *> fib.cancel
  } yield ()

  def openSourceReader: BufferedSource = Source.fromFile("effectsForNewbie/src/main/resources/data.txt")

  val sourceProgram = IO(openSourceReader).bracket { source =>
    IO(source.getLines().map(line => {Thread.sleep(100); line}).foreach(println))
  } { source =>
    IO(source.close()).void
  }

  override def run: IO[Unit] = sourceProgram.void

}
