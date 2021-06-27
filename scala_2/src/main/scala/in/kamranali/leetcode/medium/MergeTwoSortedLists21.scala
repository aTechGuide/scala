package in.kamranali.leetcode.medium

object MergeTwoSortedLists21 {

  case class ListNode(var x: Int, var next: ListNode = null)
    def mergeTwoLists(l1: ListNode, l2: ListNode): ListNode = {
        
        def merge(node1: ListNode, node2: ListNode, head: ListNode, prev: ListNode): ListNode = {
            
            if (node1 == null || node2 == null) {
                
                if (prev != null) {
                    prev.next = if (node1 != null) {
                        node1
                    } else if (node2 != null) {
                        node2
                    } else null
                }
                
                head
            }
            else if (head == null) {
                if (node1.x < node2.x) {
                    val newHead = node1
                    
                    merge(node1.next, node2, newHead, newHead)
                } else {
                    val newHead = node2
                    
                    merge(node1, node2.next, newHead, newHead)
                }
            } else {
                if (node1.x < node2.x) {
                    val temp = node1.next
                    prev.next = node1
                    
                    merge(temp, node2, head, node1)
                } else {
                    val temp = node2.next
                    prev.next = node2
                    
                    merge(node1, temp, head, node2)
                }
            }
            
        }
        
        if (l1 == null) l2
        else if (l2 == null) l1
        else merge(l1, l2, null, null)
        
    }

  def main(args: Array[String]): Unit = {

    val l1 = ListNode(1)
    val l2 = ListNode(2)

    println(mergeTwoLists(l1, l2))
  }
}