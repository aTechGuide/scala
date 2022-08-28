package guide.atech.algorithms.cflt.lru.timestamp

import guide.atech.algorithms.cflt.lru
import guide.atech.algorithms.cflt.lru.timestamp.NodeTimestamp

class DoublyLinkedListTimestamp {
  // last ------------- head
  private var OptionalHead: Option[NodeTimestamp]  = None
  private var OptionalLast: Option[NodeTimestamp] = None

  def addFront(node: NodeTimestamp): Unit = {
    val nodeOption = Some(node)
    if (OptionalHead.isEmpty) { // scenario for first node
      OptionalHead = nodeOption
      OptionalLast = nodeOption
    } else {
      val head = OptionalHead.head
      head.next = nodeOption
      node.prev = OptionalHead

      OptionalHead = nodeOption
    }
  }

  def removeAndReturnLastElement(): NodeTimestamp = {
    val last = OptionalLast.head
    OptionalLast = last.next

    last.next.head.prev = None
    last
  }

  def bringNodeToFront(node: NodeTimestamp): Unit = {
    val head = OptionalHead.head
    val last = OptionalLast.head

    if (!(head == last || head == node)) { // !(Only one node || already at head)
      // Step 1: Remove the node from the DLL
      removeNode(node)

      // Step 2: Bring the node to the front
      head.next = Some(node)
      node.prev = OptionalHead
      node.next = None

      OptionalHead = Some(node)
    }
  }

  def removeNode(node: NodeTimestamp): Unit = {

    val last = OptionalLast.head
    val nodePrev = node.prev
    val nodeNext = node.next

    if (node == last) {
      // this is last node adjust accordingly
      OptionalLast = nodeNext
      nodeNext.head.prev = None
    } else {
      // Remove the node from the middle
      nodePrev.head.next = nodeNext
      nodeNext.head.prev = nodePrev
    }
  }

  def removeExpiredNodes(boundary: Long): List[NodeTimestamp] = {

    def iterate(node: NodeTimestamp, acc: List[NodeTimestamp]): List[NodeTimestamp] = {
      val timestamp = node.timestamp

      if (node == OptionalLast.last) {
        if (timestamp <= boundary) acc
        else {
          removeNode(node)
          node :: acc
        }
      } else {
        if (timestamp <= boundary) {
          iterate(node.next.head, acc)
        } else {
          val nextNode = node.next.head
          removeNode(node)
          iterate(nextNode, node :: acc)
        }

        iterate(node.next.head, acc)
      }
    }

    iterate(OptionalHead.head, List())
  }

  def printList(): Unit = {

    def printUtil(node: NodeTimestamp): Unit = {
      if (node == OptionalHead.head) {
        print(s"(${node.key}, ${node.value})")
      } else {
        print(s"(${node.key}, ${node.value}) -> ")
        printUtil(node.next.head)
      }
    }

    printUtil(OptionalLast.head)
    println()
  }
}
