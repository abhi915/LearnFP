import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import akka.stream.scaladsl.Sink
import akka.stream.scaladsl.Flow

object FirstPrinciple extends  App {

  implicit val system = ActorSystem("FirstPrinciple")
  implicit val materializer = ActorMaterializer()

  val source: Source[Int, NotUsed] = Source(1 to 10)
  val sink = Sink.foreach[Int](println)
  val graph = source.to(sink)






  graph.run()(materializer)

  //
  val flow: Flow[Int, Int, NotUsed] = Flow[Int].map(x => x + 1)

  val sourceWithFlow = source.via(flow)
  val floWithSink = flow.to(sink)

  val data = sourceWithFlow.to(sink).run()


  // nulls are not allowed
  // val illegalSource = Source.single[String](null)
  // we need to use either option or sequence without null


  // sinks
  val theMostBoringSink = Sink.ignore
  val forEachSink = Sink.foreach[String](println)
  val headSink = Sink.head[Int]
  val foldSink = Sink.fold[Int, Int](0)((a, b) => a + b)
  // we have drop, filter, take, takeWhile, etc.
  // we don't have flatMap
  val mapFlow = Flow[Int].map(x => x * 2)
  val takeFlow = Flow[Int].take(5)


  val doubleFlowGraph = source.via(mapFlow).via(takeFlow).to(sink)

  // Syntactical sugars
  val mapSource = Source(1 to 10).map(x => x * 2)

  // operator - component

}
