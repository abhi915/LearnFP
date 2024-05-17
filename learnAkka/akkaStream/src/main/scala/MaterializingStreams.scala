import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import akka.stream.scaladsl.Sink
import akka.stream.scaladsl.Flow
import akka.stream.scaladsl.Keep

import scala.util.{Failure, Success}

object MaterializingStreams extends  App {

  implicit val system = ActorSystem("MaterializingStreams")
  implicit val mat = ActorMaterializer()

  val simpleGraph = Source(1 to 10).to(Sink.foreach(println))
  // val simpleMatValue = simpleGraph.run()

  val source = Source(1 to 10)
  val sink = Sink.reduce[Int]((a, b) => a + b)
  val sumFuture = source.runWith(sink)
  implicit val ec = system.dispatcher
  sumFuture.onComplete{
    case Success(value) => println(s"The sum of all elements is: $value")
    case Failure(exception) => println(s"Summing all elements failed with: $exception")
  }

  val simpleSource = Source(1 to 10)
  val simpleFlow = Flow[Int].map(x => x + 1)
  val simpleSink = Sink.foreach[Int](println)
  simpleSource.viaMat(simpleFlow)(Keep.right).toMat(simpleSink)(Keep.right).run().onComplete{
    case Success(_) => println("Stream processing finished")
    case Failure(exception) => println(s"Stream processing failed with: $exception")
  }
}
