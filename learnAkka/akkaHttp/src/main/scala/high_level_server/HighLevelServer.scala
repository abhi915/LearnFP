package high_level_server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Route

object HighLevelServer extends App {
  implicit val system = ActorSystem("root actor")
  implicit val mat = ActorMaterializer()

  // directives
  import akka.http.scaladsl.server.Directives._

  val simpleRoute: Route =
    path("home") { // Directives
      complete(StatusCodes.OK)
    }

  Http().bindAndHandle(simpleRoute, "localhost",8080)

}
