/*
HashMap
ListMap
SortedMap - TreeMap
 */



case class Student(id: Int, name: String, div: String)

import scala.collection.immutable.{HashMap, TreeMap}

val h  = List(
  Student(3, "Dhanu", "class 5"),
  Student(2, "Dhritee", "class 11"),
  Student(1, "a", "class 10"),
  Student(4, "gopi", "class 10"),
  Student(4, "bhavana", "class 10")
)
  .map( s => (s.id, s))



val tm = TreeMap.from(h)(Ordering[Int].reverse)



