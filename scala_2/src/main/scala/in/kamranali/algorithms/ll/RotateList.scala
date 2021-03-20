package in.kamranali.algorithms.ll

/**
  * https://www.interviewbit.com/problems/rotate-list/
  */

class RotateList {

  import RotateList._

  def rotateRight(A: ListNode, B: Int): ListNode  = {

    var length = 0
    var last: ListNode = null
    var cur = A

    while (cur != null) {
      last = cur
      length += 1
      cur = cur.next
    }

    // Normalize B
    val rotate = B % length

    if (rotate == 0 || A.next == null) {
      return A
    }

    cur = A
    for ( _ <- 0 until length - (rotate + 1)) {
      cur = cur.next
    }

    val head = cur.next
    cur.next = null
    last.next = A

    printList(head)

    head
  }

  def printList(root: ListNode): Unit = {

    var curr = root
    while (curr != null) {

      print(s"${curr.value} \t")
      curr = curr.next
    }
  }

}

object RotateList extends App {

  case class ListNode(var value: Int, var next: ListNode = null)

  val sol = new RotateList()

  // Test 1
  var A = ListNode(1)
  A.next = ListNode(2)
  A.next.next = ListNode(3)
  A.next.next.next = ListNode(4)
  A.next.next.next.next = ListNode(5)

  var data: ListNode = sol.rotateRight(A, 2)

}
