package effectsBasics

import cats._
import cats.implicits._
import cats.effect._
import cats.effect.unsafe.implicits.global
import scala.io.StdIn


object MyApp {
   object Console {
     def putStrln(s: String): IO[Unit] = IO(println(s))
     def getStrln: IO[String] = IO(StdIn.readLine())
   }

  def main(args: Array[String]) = {
    val io = Console.putStrln("hello......")
    io.unsafeRunSync()


    import Console._
    val program =
      for {
        _ <- putStrln("Enter the input")
        name <- getStrln
        _ <- putStrln(s"Welcome ${name} in scala world.....")
      } yield ()
    program.unsafeRunSync()


    val program2 = putStrln("Enter the input") *> getStrln.flatMap(name => putStrln(s"Welcome ${name} in scala world....."))

    val program3 = putStrln("Enter the input") >> getStrln.flatMap(name => putStrln(s"Welcome ${name} in scala world....."))

    program2.unsafeRunSync()

  }



}
