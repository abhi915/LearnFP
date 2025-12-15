import scala.collection.mutable.ArrayBuffer

/*
   9 6 7 1 5
   6 9 7 1 5

 */


def insertionSort(list: List[Int], acc: List[Int]): List[Int] =
  (list, acc) match {
    case (Nil, _) => acc
    case (head::tail, Nil) => insertionSort(tail, head::acc)
    case (head::tail, headAcc:: tailAcc) =>
      if (head > headAcc) insertionSort(tail, head::acc) else {
      headAcc::insertionSort(list, tailAcc)
    }
  }

val l = insertionSort(List(9,6,7,1,5), List.empty[Int]).reverse

val x = List( 1, 3, 2, 8, 7, 4).dropWhile( _ % 2 != 0)

val res = List( 1, 3, 2, 8, 7, 4).partition(_%2==0)


val l1 = List("abc", "bac", "xyz", "opq")

l1
  .groupBy(s => s.sorted )
  .values
  .toList


val xs = List(1, 2, 3, 4, 5)
val m = xs.grouped(3)
  m.next()
  m.next()

val k =  xs.sliding(3)
 k.next()
 k.next()


xs.zipWithIndex.map( a => (a._2, a._1)).mkString("[", ",", "]")

xs.indices.foreach(i => println(xs(i)))

