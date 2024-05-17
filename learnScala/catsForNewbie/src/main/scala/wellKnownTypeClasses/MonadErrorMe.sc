import cats._
import cats.implicits._

trait HttpMethod

case object Get extends HttpMethod

case class HttpRequest(method: HttpMethod, url: String)

case class HttpResponse(status: Int)



val optionME: MonadError[Option, Unit] = new MonadError[Option, Unit] {
  override def raiseError[A](e: Unit): Option[A] = None

  override def handleErrorWith[A](fa: Option[A])(f: Unit => Option[A]): Option[A] = fa.orElse(f(()))

  override def pure[A](x: A): Option[A] = Some(x)

  override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa match {
    case Some(data) => f(data)
    case None       => None
  }

  override def tailRecM[A, B](a: A)(f: A => Option[Either[A, B]]): Option[B] = ???
}

object MonadErrorInstance{

  def eitherMe[E]: MonadError[Either[E, *], E] = new MonadError[Either[E, *], E] {
    override def pure[A](x: A): Either[E, A]= ???

  }
}
