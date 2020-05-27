package in.kamranali.algorithms.ll

case class ListNode(var data: Int, var next: ListNode = null)

object CustomLL {

  def printList(root: ListNode): Unit = {

    var curr = root
    while (curr != null) {

      println(curr.data)
      curr = curr.next
    }
  }

  def reverse(root: ListNode): ListNode = {

    var prev: ListNode = null

    var curr = root

    while (curr != null) {

      var next = curr.next
      curr.next = prev

      prev = curr
      curr = next
    }

    prev
  }

  def main(args: Array[String]): Unit = {

    val root = ListNode(1)
    root.next = ListNode(2)
    root.next.next = ListNode(3)

    printList(root)

    val rev = reverse(root)

    printList(rev)



  }

}
