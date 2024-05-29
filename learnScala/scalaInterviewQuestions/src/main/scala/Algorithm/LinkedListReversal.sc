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

