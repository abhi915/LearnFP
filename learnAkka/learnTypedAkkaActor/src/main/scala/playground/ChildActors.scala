package playground

import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.Behavior
import akka.actor.typed.ActorSystem

object ChildActors {
  object Parent {
    trait Command

    case class CreateChild(message: String) extends Command

    case class TellChild(message: String) extends Command

    def apply(): Behavior[Command] = Behaviors.receive { (context, message) =>
      message match {
        case CreateChild(name) =>
          context.log.info(s"Creating child with name $name")
          // creating child actor
          val childRef = context.spawn(Child(), name)
          active(childRef)
      }
    }

    def active(childRef: ActorRef[String]): Behavior[Command] = Behaviors.receive { (context, message) =>
      message match {
        case TellChild(message) =>
          childRef ! message
          Behaviors.same
        case _ => context.log.info("[Parent] I cannot understand this message")
          Behaviors.same
      }
    }

  }

  object Child {
    def apply(): Behavior[String] = Behaviors.receiveMessage { message =>
      println(s"I am a child actor and I received $message")
      Behaviors.same
    }
  }

  def demoParentChild(): Behavior[Unit] = Behaviors.setup { context =>

    import Parent._
    val parent = context.spawn(Parent(), "ParentChildDemo")
    parent ! CreateChild("child1")
    parent ! TellChild("Hey Kid!")
    Behaviors.empty
  }

      val system = ActorSystem(demoParentChild(), "ParentChildDemo")
      Thread.sleep(1000)
      system.terminate()

}

