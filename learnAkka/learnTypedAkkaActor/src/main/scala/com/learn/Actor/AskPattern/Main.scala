package com.learn.Actor.AskPattern

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior}

object Main {

  trait Root

  def apply(): Behavior[Root] = Behaviors.setup { context =>

    val halAcotr = context.spawn(Hal(), "halActor")
    val daveActor = context.spawn(Dave(halAcotr), "daveActor")
    Behaviors.same
  }

  def main(args: Array[String]) = {
     val system = ActorSystem(Main(), "mainActorSystem")
    Thread.sleep(1000)
    system.terminate()
  }


}
