package transform

import cats.effect.{IO, IOApp}
import fs2.Stream

object Chunks extends IOApp.Simple {

  override def run: IO[Unit] = {
    val data = Stream(1,2,3,4)
    IO.println(data.chunks.toList)
  }

}
