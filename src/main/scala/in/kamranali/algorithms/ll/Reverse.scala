package in.kamranali.algorithms.ll

case class Reverse() {

  import Reverse._

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
}

object Reverse {

  case class ListNode(var value: Int, var next: ListNode = null)

  def main(args: Array[String]): Unit = {

    val root = ListNode(1)
    root.next = ListNode(2)
    root.next.next = ListNode(3)

    printList(root)

    val rev = Reverse().reverse(root)

    printList(rev)
  }

  def printList(root: ListNode): Unit = {

    var curr = root
    while (curr != null) {

      print(s"${curr.value} \t")
      curr = curr.next
    }
  }

}
