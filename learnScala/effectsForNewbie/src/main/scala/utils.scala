import cats.effect.IO

/*
scala 3 has extension method. package is removed in scla3
 */

package object utils {
  implicit class DebugWrapper[A](io: IO[A]) {
    def debug: IO[A] =
      for {
        a <- io
        _ = println(s"${Thread.currentThread().getName} - $a")
      } yield a
  }
}
