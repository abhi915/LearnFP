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

insertionSort(List(9,6,7,1,5), List.empty[Int]).reverse