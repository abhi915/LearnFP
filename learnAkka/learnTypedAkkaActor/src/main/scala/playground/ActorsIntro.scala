package playground

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import scala.concurrent.ExecutionContext

object ActorsIntro {

  val simpleActorBehaviour = Behaviors.receiveMessage[String] {
    message => {
      println(s"I have received a message: $message")
      Behaviors.same
    }
  }

  def demoSimpleActor() = {
    val system = ActorSystem(SimpleActor(), "FirstActorSystem")
    system ! "I am learning akka"
    Thread.sleep(1000)
    system.terminate()
  }

  def main(args: Array[String]): Unit = {
    demoSimpleActor()
  }

}

object SimpleActor {
  def apply(): Behavior[String] = Behaviors.receiveMessage[String] {
    message => {
      println(s"I have received a message: $message")
      Behaviors.same
    }
  }
}

object SimpleActor_v2 {
  def apply(): Behavior[String] = Behaviors.receive {  (context, message) => {
      println(s"I have received a message: $message")
      Behaviors.same
    }
  }
}


object SimpleActor_v3  {
  def apply(): Behavior[String] = Behaviors.setup[String] {
    context => {
    // actor private data methods, behaviors etc.
     //

    // behavior used for first message
    Behaviors.receiveMessage {
      message =>
        println(s"I have received a message: $message")
        Behaviors.same
    }
  }
  }
}
// Best practices:
//

object Person {
  def happy() : Behavior[String] = Behaviors.receive { (context, message) =>
    message match {
      case "Akka sucks" =>
        context.log.info("I am sad")
        Behaviors.same
      case _ =>
        context.log.info("I am happy")
        Behaviors.same
    }
  }
  def sad(): Behavior[String] = Behaviors.receive { (context, message) =>
    message match {
      case "Akka is awesome" =>
        context.log.info("I am happy")
        Behaviors.same
      case _ =>
        context.log.info("I am sad")
        Behaviors.same
    }
  }

    def apply() = happy()
}

// Type safety is important in Akka. add wrapper type and type hierarchy (case class/object)

object BetterActor{
  trait Message
  case class IntMessage(value: Int) extends Message
  case class StringMessage(value: String) extends Message

  def apply(): Behavior[Message] = Behaviors.receive((context, message) => {
    message match {
      case IntMessage(value) =>
        context.log.info(s"Received Int: $value")
        Behaviors.same
      case StringMessage(value) =>
        context.log.info(s"Received String: $value")
        Behaviors.same
    }
  })
}