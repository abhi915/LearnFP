package lowLevelServer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.IncomingConnection
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethod, HttpMethods, HttpRequest, HttpResponse, StatusCode, StatusCodes}

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Success
import scala.util.Failure


object LowLevelAPI extends  App {

  implicit val system = ActorSystem("LowLevelServerApi")
  implicit val materializer = ActorMaterializer()
  import system.dispatcher

 /* val serverSource = Http().bind("localhost",8080)
  val connectioSink = Sink.foreach[IncomingConnection]{ connection =>
  println(s"Accepted incoming connection: ${connection.remoteAddress}")
  }

  val serverBindingFuture = serverSource.to(connectioSink).run()


  serverBindingFuture.onComplete{
    case Success(binding) =>
      println("Server binding successful.")
      binding.terminate(2 second)
    case Failure(ex) => println(s"Server binding failedL ${ex}")
  }*/

  val requestHandler: HttpRequest => HttpResponse = {
    case HttpRequest(HttpMethods.GET, _, _, _, _) =>
      HttpResponse(
        StatusCodes.OK,
        entity = HttpEntity(
          ContentTypes.`text/html(UTF-8)`,
          """
            |<html>
            |<body>
            |Hello akkaHttp
            |</body>
            |</htmlL>
            |""".stripMargin
        )
      )
    case request: HttpRequest =>
      HttpResponse(
        StatusCodes.NotFound,
        entity = HttpEntity(
          ContentTypes.`text/html(UTF-8)`,
          """
            |<html>
            |<body>
            |Resource can't be found.
            |</body>
            |</htmlL>
            |""".stripMargin
        ))
  }

      val httpSyncHandler = Sink.foreach[IncomingConnection]{ connection =>
        connection.handleWithSyncHandler(requestHandler)
      }
     //Http().bind("localhost",8080) .runWith(httpSyncHandler)

      // shortcut
      Http().bindAndHandleSync(requestHandler,"localhost",8080)

}
