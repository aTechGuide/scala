package guide.atech.algorithms.cflt.lru.basic

import guide.atech.algorithms.cflt.lru
import guide.atech.algorithms.cflt.lru.basic.Node

class DoublyLinkedList {
  // last ------------- head
  private var OptionalHead: Option[Node]  = None
  private var OptionalLast: Option[Node] = None

  def addFront(node: Node): Unit = {
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

  def removeAndReturnLastElement(): Node = {
    val last = OptionalLast.head
    OptionalLast = last.next

    last.next.head.prev = None
    last
  }

  def bringNodeToFront(node: Node): Unit = {
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

  private def removeNode(node: Node): Unit = {

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

  def printList(): Unit = {

    def printUtil(node: Node): Unit = {
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
