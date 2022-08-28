package guide.atech.algorithms.cflt.lru.basic

import guide.atech.algorithms.cflt.lru.basic.{DoublyLinkedList, Node}

class LRUCache(capacity: Int) {
  import scala.collection.mutable

  private val mapping: mutable.Map[Int, Node] = mutable.Map.empty[Int, Node]
  private val dll = new DoublyLinkedList()
  private var count: Int = 0

  def get(key: Int): Int = synchronized {
    /*
      Search for Key in the Map,
        - if found, go to Node and return the value + Move to the front
        - if not found, return -1
    */
    if (! mapping.contains(key)) -1
    else {
      this.put(key, mapping(key).value)
      mapping(key).value
    }
  }


  def put(key: Int, value: Int): Unit = synchronized {
    /*
      1 If key NOT present in Map,
        - Create a Node + Keep a mapping of Node in Map
        - Add Node to front of list + Increment the count + Adjust the list if required
      2 If key is present in Map
        - Move the key to front of the list + Update the value of key
    */

    if (mapping.contains(key)) {
      // case 2 [Moving key to front]

      val node = mapping(key)

      // Step 1: Updating the value
      node.value = value

      // Step 2: Bring Node to the front
      dll.bringNodeToFront(node)
    }
    else {
      // Step 1: Create a node and add to mapping
      val node = Node(key, value)
      mapping.put(key, node)

      // Step 2: Add Node to front of list
      dll.addFront(node)

      // Step 3: Update the counter and verify the constraint
      count = count + 1

      if (count > capacity) {
        // decrease the count
        count = count - 1
        val lastNode = dll.removeAndReturnLastElement()

        // remove the last element from the mapping
        mapping.remove(lastNode.key)
      }
    }
  }

}