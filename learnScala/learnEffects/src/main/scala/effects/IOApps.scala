package effects

import cats.effect.{ExitCode, IO, IOApp}

object IOApps extends IOApp {

  import IOCode._

  def run(args: List[String]): IO[ExitCode] = {
    program.as(ExitCode.Success)
  }
}


  object IOAppsSimple extends IOApp.Simple {
    import  IOCode._
    def run: IO[Unit] = program
  }


object IOCode {
  def program = for {
    line <- IO.delay(scala.io.StdIn.readLine())
    _    <- IO.delay(println(s"you said $line"))
  } yield ()
}

