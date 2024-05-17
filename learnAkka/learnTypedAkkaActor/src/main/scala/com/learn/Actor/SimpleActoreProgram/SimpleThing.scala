package com.learn.Actor.SimpleActoreProgram

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors


trait SimpleThing

case object EatChocolate extends SimpleThing
case object WashDishes extends SimpleThing
case object LearnAkka extends SimpleThing


object SimpleThing {

  def emotionalFunctionActor(happiness: Int = 0) : Behavior[SimpleThing] = Behaviors.receive { (context, message) =>
      message match {
        case EatChocolate =>
          context.log.info(s"($happiness) eating chocolate")
          emotionalFunctionActor(happiness+1)
        case WashDishes =>
          context.log.info(s"($happiness) washing dishes, womp womp")
          emotionalFunctionActor(happiness-2)
        case LearnAkka =>
          context.log.info(s"($happiness) Learning akka, yes!!")
          emotionalFunctionActor(happiness+100)
        case _ =>
          context.log.info(s"receive something, I don't know")
          Behaviors.same
      }
  }
}


