package guide.atech.algorithms.cflt.lru

case class Node(key: Int, var value: Int, var next: Node = null, var prev: Node = null)

class LRUCache(capacity: Int) {
  import scala.collection.mutable

  private val mapping: mutable.Map[Int, Node] = mutable.Map.empty[Int, Node]

  private var head: Node = null
  private var last: Node = null
  private var count: Int = 0

  def get(key: Int): Int = synchronized {
    // Search for Key in the Map, if found 
    // if found, go to Node and return the value + Move to the front
    // if not found, return -1
    if (! mapping.contains(key)) -1
    else {
      this.put(key, mapping(key).value)
      mapping(key).value
    }
  }

  // last ------------- head
  def put(key: Int, value: Int): Unit = synchronized {
    // 1 If key NOT present in Map, 
    // Create a Node + Keep a mapping of Node in Map + Add Node to front of list + Increment the count + Adjust the list if required
    // 2 If key is present in Map
    // Move the key to front of the list + Update the value of key

    if (mapping.contains(key)) {
      // case 2 [Moving key to front]

      val node = mapping(key)

      // Step 1: Updating the value
      node.value = value

      // Step 2: Bring Node to the front
      if (!(head == last || head == node)) { // !(Only one node || already at head)
        val nodePrev = node.prev
        val nodeNext = node.next
        if (node == last) {
          // this is last node adjust accordingly
          last = nodeNext
          nodeNext.prev = null
        } else {
          nodePrev.next = nodeNext
          nodeNext.prev = nodePrev
        }

        // Adjust the front node
        head.next = node
        node.prev = head
        node.next = null

        head = node
      }
    }
    else {
      // Step 1: Create a node and add to mapping
      val node = Node(key, value)
      mapping.put(key, node)

      // Step 2: Add Node to front of list
      if (head == null) { // scenario for first node
        head = node
        last = node
      } else {
        head.next = node
        node.prev = head
        head = node
      }

      // Step 3: Update the counter and verify the constraint
      count = count + 1

      if (count > capacity) {
        // decrease the count
        count = count - 1

        // remove the last element from the mapping
        mapping.remove(last.key)

        // remove the last element from the list
        last = last.next
        last.prev = null
      }
    }
  }

  // Auxiliary Method
  private def printList(): Unit = {

      def printUtil(node: Node): Unit = {
        if (node == head) {
          print(s"(${node.key}, ${node.value})")
        } else {
          print(s"(${node.key}, ${node.value}) -> ")
          printUtil(node.next)
        }
      }

      printUtil(last)
      println()
    }

}