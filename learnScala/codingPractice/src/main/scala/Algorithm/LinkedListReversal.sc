

/* Immutable case*/
def reverse(listData: List[Int], acc: List[Int]): List[Int] = listData match {
    case head::Nil => head::acc
    case head::tail => reverse(tail, head:: acc)
  }

reverse(List(1,2,3, 4), List.empty[Int]).mkString("(",",",")")






/* In place reversal, mutable case*/
case class Node(var data: Int, var next: Node)

def reverseLinkedList(head: Node): Node = {
  var prev: Node = null
  var current = head
  var next: Node = null

  while (current != null) {
    next = current.next
    current.next = prev
    prev = current
    current = next
  }
  prev
}

val head = Node(1, Node(2, Node(3, Node(4, null))))

reverseLinkedList(head)

