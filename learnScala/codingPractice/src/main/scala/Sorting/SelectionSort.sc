/*
i=0 to n
find the min element and put at i = 0
i=1 to n
find min elemetn and put at i =1
 */
import scala.annotation.tailrec


@tailrec
def selectionSort(remaining: List[Int], acc: List[Int]): List[Int] =
  if (remaining.isEmpty) acc else selectionSort(remaining.filterNot(_ == remaining.min),
    remaining.min :: acc)

selectionSort(List(1,5,2,9,11, 3,7), List.empty[Int])