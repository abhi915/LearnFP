import scala.annotation.tailrec

/*

 compare with adjacent element and largest element place at righmost side.

 */

/**
 * Definition for singly-linked list.
 *
 *   3 4 2
 *   5 6 7
 *
 *   2 4 3
 *   7 6 5
 *
 */
  class ListNode(_x: Int = 0, _next: ListNode = null) {
    var next: ListNode = _next
    var x: Int = _x
  }


private case class NodeDetails(currentNode: ListNode, extra: Int)

 
  def addCustomTwoNumber(l1:ListNode, l2: ListNode, nodeDetails: NodeDetails): Unit = {
    if (l1 == null && l2==null && nodeDetails.extra ==0) ()
    else if (l1 == null && l2==null && nodeDetails.extra !=0) nodeDetails.currentNode.next = new ListNode(nodeDetails.extra)
    else if (l1 != null && l2 != null){
      nodeDetails.currentNode.next = new ListNode((l1.x + l2.x + nodeDetails.extra)%10)
      addCustomTwoNumber(l1.next, l2.next, NodeDetails(nodeDetails.currentNode.next, (l1.x + l2.x + nodeDetails.extra)/10))
    } else if(l1 != null) {
      nodeDetails.currentNode.next = new ListNode((l1.x + nodeDetails.extra)%10)
      addCustomTwoNumber(l1.next, null, NodeDetails(nodeDetails.currentNode.next, (l1.x  + nodeDetails.extra)/10))
    } else {
      nodeDetails.currentNode.next = new ListNode((l2.x + nodeDetails.extra)%10)
      addCustomTwoNumber(null,l2.next, NodeDetails(nodeDetails.currentNode.next, (l2.x  + nodeDetails.extra)/10))
    }
  }

  def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
    if (l1 != null && l2 != null) {
      val headNode = new ListNode((l1.x + l2.x) % 10)
      val extra = (l1.x + l2.x)/10
      addCustomTwoNumber(l1.next, l2.next, NodeDetails(headNode, extra))
      headNode
    }else if (l1 != null) {
      val headNode = new ListNode((l1.x) % 10)
      val extra = (l1.x)/10
      addCustomTwoNumber(l1.next, null, NodeDetails(headNode, extra))
      headNode
    } else {
      val headNode = new ListNode((l2.x) % 10)
      val extra = (l2.x)/10
      addCustomTwoNumber(null, l2.next, NodeDetails(headNode, extra))
      headNode
    }
  }



val n1 = new ListNode(3, new ListNode(4, new ListNode(6)))
val n2 =  new ListNode(5, new ListNode(9, new ListNode(3)))




@tailrec
def printResult(l: ListNode): Unit = {
  if (l == null) ()
  else {print(l.x); printResult(l.next)}
}

printResult(addTwoNumbers(n1, n2))

