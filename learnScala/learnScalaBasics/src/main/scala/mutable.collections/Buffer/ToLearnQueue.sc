import scala.collection.mutable

val x : mutable.Queue[Int] = new mutable.Queue[Int]()

x.enqueue(1)
x.enqueue(2)

x.dequeue()