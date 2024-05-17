package com.learn.Actor.SimpleActoreProgram

import akka.actor.typed.ActorSystem


object SimpleActorProgramDriver {

  def main(args: Array[String]) = {

    println("here")
    val emotionalActorSystem = ActorSystem(SimpleThing.emotionalFunctionActor(), "emotionalSystem")
    emotionalActorSystem ! EatChocolate
    emotionalActorSystem ! EatChocolate
    emotionalActorSystem ! WashDishes
    emotionalActorSystem ! LearnAkka

    println("here2")
    Thread.sleep(1000)
    emotionalActorSystem.terminate()
  }

}
