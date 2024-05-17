package effectsBasics

import scala.io.StdIn
import cats.effect.{ExitCode, IO, IOApp}

object MyAPPIO extends  IOApp {

  object Console {
    def putStrln(s: String): IO[Unit] = IO(println(s))

    def getStrln(text: String): IO[String] = IO(StdIn.readLine(text))
  }
  override def run(args: List[String]): IO[ExitCode] = {

    import Console._

    for {
      s <- getStrln("Enter name...")
      t <- getStrln("Enter sirname....")
      _ <- putStrln("Full Name  " + s ++ " " ++ t)
    } yield ExitCode.Success

  }

}
