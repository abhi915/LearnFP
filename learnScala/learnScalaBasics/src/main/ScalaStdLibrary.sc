/*
root
  -scala
    - annotation
    - beans
    - collections
    - compat
    - concurrent
    - io
    - math
    - ref
    - reflect
    - runtime
    - sys
    - text
    - util
  - Any
  - AnyVal
  - App
  - Array
  - Boolean
  - Byte
  - Char
  - Cloneable
  - Console
  - DelayedInit
  - Double
  - Dynamic
  - Enumeration
  - Equals
  - FallBackArrayBuilding
  - Float
  - Function
  - Function0 to Function22
  - Int
  - Long
  - MatchError
  - Nil
  - None
  - NotNull
  - Null
  - Nothing
  - Option
  - PartialFunction
  - Predef
  - Product0 to Product22
  - Proxy
  - Tuple1 to Tuple22
  - Unit
  - UninitializedFieldError
  - UnsupportedOperationException
  - deprecated
  - deprecatedInheritance
  - deprecatedName
  - deprecatedOverriding
  - inline
  - language
  - native
  - noinline
  - remote
  - specialized
  - throws
  - transient
  - uncheked
  - volatile
 */

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


def x = Future {

  Thread.sleep(1000)
  100
}

def y = Future {
  Thread.sleep(100)
  10
}

import scala.collection.immutable.Seq
val f = Seq(x,y)

Future
  .sequence(f)
