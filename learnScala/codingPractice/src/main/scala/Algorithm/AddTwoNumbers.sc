/**
 * Problem: Add two linked list.
 *
 * Definition for singly-linked list.
 *
 *   3 4 2
 *   5 6 7
 *
 *
 *   lower digit -> higher digit
 *   2 4 3
 *   7 6 5
 *-------------
 *   9 0 9
 */


case class ListNode(x: Int, nextNode: ListNode)

val n1 = List(2,4,3)
val n2 = List(7,6,5,2,3)



//
//  243
//76523
//    76766


def addTwoNumbers(n1: List[Int], n2: List[Int], extra:Int,  acc: List[Int]): List[Int] = {
  (n1, n2) match {
    case (Nil, Nil) =>  if (extra != 0) extra::acc else acc
    case (head1 :: tail1, Nil) => addTwoNumbers(tail1, Nil, (head1 + extra) / 10, (head1 + extra) % 10 :: acc)
    case (Nil, head2 :: tail2) => addTwoNumbers(Nil, tail2, (head2 + extra) / 10, (head2 + extra) % 10 :: acc)
    case (head1::tail1, head2:: tail2) => addTwoNumbers(tail1, tail2, (head1 + head2 + extra)/10, (head1+head2 + extra)%10 :: acc)
  }
}

addTwoNumbers(n1.reverse, n2.reverse, 0, List.empty[Int])