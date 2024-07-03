/*



 */

// low1 ---> high1
// low2 ---> high2

// high  --> low

def mergeSort(left: List[Int], right: List[Int], acc: List[Int]) : List[Int] =
  (left, right) match {
    case (Nil, Nil) => acc.reverse
    case (head::tail, Nil) => mergeSort(tail, Nil, head :: acc)
    case (Nil, head::tail) => mergeSort(Nil, tail, head :: acc)
    case (head1::tail1, head2::tail2) => if (head1 <= head2) mergeSort(tail1, right, head1::acc) else mergeSort(left, tail2, head2::acc)
  }



def sort(data: List[Int]): List[Int] = {
  val mid = data.size/2

  if (mid == 0) data
  else {
    val (left, right) = data.splitAt(mid)
   val sortedData = mergeSort(sort(left), sort(right), List.empty[Int])
    println(sortedData)
    sortedData
  }
}


sort(List(6,1,3,5,9,8,2,7)).mkString("(",",",")")