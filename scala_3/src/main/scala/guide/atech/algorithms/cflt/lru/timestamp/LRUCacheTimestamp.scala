package guide.atech.algorithms.cflt.lru.timestamp

import guide.atech.algorithms.cflt.lru.timestamp.{DoublyLinkedListTimestamp, NodeTimestamp}

import scala.annotation.unused

class LRUCacheTimestamp(capacity: Int, windowSize: Int) {
  import scala.collection.mutable

  private val mapping: mutable.Map[Int, NodeTimestamp] = mutable.Map.empty[Int, NodeTimestamp]
  private val dll = new DoublyLinkedListTimestamp()
  private var count: Int = 0

  def get(key: Int): Int = synchronized {
    /*
      Search for Key in the Map,
        - if found, go to Node and return the value + Move to the front
        - if not found, return -1
    */
    if (!mapping.contains(key)) -1
    else {
      val boundary = System.currentTimeMillis() + windowSize

      val node = mapping(key)
      if(node.timestamp <= boundary) {
        this.put(key, node.value, node.timestamp) //<- bringing the node to the front
        node.value
      }
      else {
        dll.removeNode(node)
        mapping.remove(key)
        count = count -1
        -1
      }
    }
  }


  def put(key: Int, value: Int, timestamp: Long): Unit = synchronized {
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
      node.timestamp = timestamp

      // Step 2: Bring Node to the front
      dll.bringNodeToFront(node)
    }
    else {
      // Step 1: Create a node and add to mapping
      val node = NodeTimestamp(key, value, timestamp)
      mapping.put(key, node)

      // Step 2: Add Node to front of list
      dll.addFront(node)

      // Step 3: Update the counter and verify the constraint
      count = count + 1

      if (count > capacity) {

        val expiredNodesList = dll.removeExpiredNodes(System.currentTimeMillis())

        if (expiredNodesList.nonEmpty) {
          count = count - expiredNodesList.length

          expiredNodesList.foreach { node => {
            mapping.remove(node.key)
          }}
        } else {
          // decrease the count
          count = count - 1
          val lastNode = dll.removeAndReturnLastElement()

          // remove the last element from the mapping
          mapping.remove(lastNode.key)
        }
      }
    }
  }

  def getAverage: Double = {
    val expiredNodesList = dll.removeExpiredNodes(System.currentTimeMillis())

    // Step1: Adjust the count and mapping
    count = count - expiredNodesList.length

    expiredNodesList.foreach { node => {
      mapping.remove(node.key)
    }}

    val sum = mapping.values.map(_.value).sum
    val len = mapping.size

    (sum * 1.0) / len
  }

}