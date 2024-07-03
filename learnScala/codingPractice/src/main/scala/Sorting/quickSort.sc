import scala.annotation.tailrec



def quickSort(data: List[Int]): List[Int] =
  if (data.isEmpty) List.empty
  else {
    val pivot = data.head
    val (left, right) = data.tail.partition( _ < pivot)
    quickSort(left) ::: (pivot :: quickSort(right))
  }


quickSort(List(1,6,3,5,9,8,2,7)).mkString("(",",",")")


