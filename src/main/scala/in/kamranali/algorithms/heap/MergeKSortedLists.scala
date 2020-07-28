package in.kamranali.algorithms.heap

/**
  *
  * Ref
  * - https://www.youtube.com/watch?v=ptYUCjfNhJY
  * - https://www.geeksforgeeks.org/merge-k-sorted-linked-lists-set-2-using-min-heap/
  * - https://leetcode.com/problems/merge-k-sorted-lists/submissions/
  */

class MergeKSortedLists {
  import MergeKSortedLists._

  def mergeKLists(lists: Array[ListNode]): ListNode = {
    import scala.collection.mutable

    // Creating Min Heap
    val predicate: Ordering[ListNode] = (a, b) => b.x compare a.x
    val heap = mutable.PriorityQueue[ListNode]()(predicate)

    var head: ListNode = null
    var last: ListNode = null

    val k = lists.length

    // Push Head of all the non Empty lists into Min Heap
    (0 until k).foreach { idx =>
      if (lists(idx) != null) heap.enqueue(lists(idx))
    }

    while (heap.nonEmpty) {

      val top = heap.dequeue()
      if (top.next != null) {
        heap.enqueue(top.next)
      }

      if (head == null) {
        head = top
        last = top
      } else {
        last.next = top
        last = top
      }
    }

    head
  }

}

object MergeKSortedLists extends App {


  case class ListNode(x: Int, var next: ListNode = null)

  val sol = new MergeKSortedLists()
  var A: Array[ListNode] = _
  var data: ListNode = _

  // Test 1
  A = Array[ListNode](ListNode(1, ListNode(4, ListNode(5))), ListNode(1, ListNode(3, ListNode(5))), ListNode(2, ListNode(6)))
  data = sol.mergeKLists(A)
  println(data)
}
