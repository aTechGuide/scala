package in.kamranali.leetcode.medium

case class Node(key: Int, var value: Int, var next: Node = null, var prev: Node = null)

class LRUCache(_capacity: Int) {
    import scala.collection.mutable
    
    private val capacity: Int = _capacity
    
    private val mapping: mutable.Map[Int, Node] = mutable.Map.empty[Int, Node]
    
    private var head: Node = null
    private var last: Node = null
    private var count: Int = 0

    def get(key: Int): Int = {
        // Search for Key in the Map, if found 
            // if found, go to Node and return the value + Move to the front
            // if not found, return -1
        if (mapping.contains(key)) {
            // Moving key to front
            val node = mapping(key)
            val nodePrev = node.prev
            val nodeNext = node.next
            
            // Adjust the gap nodes
            if (head == last || head == node) {
                // single node scenario, do nothing
            } else {
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
            
            
            // return value
            node.value 
        }
        else -1
    }

    def put(key: Int, value: Int) {
        // 1 If key NOT present in Map, 
            // Create a Node + Keep a mapping of Node in Map + Add Node to front of list + Increment the count + Adjust the list if required
        // 2 If key is present in Map
            // Move the key to front of the list + Update the value of key
        
        if (mapping.contains(key)) {
            // case 2 [Moving key to front]
            val node = mapping(key)
            val nodePrev = node.prev
            val nodeNext = node.next
            
            // Adjust the gap nodes
            if (head == last || head == node) {
                // do nothing
            } else {
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
            
            // Updating the value
            node.value = value
        }
        else {
            // case 1
            // create a node
            val node = Node(key, value)
            
            // keep a mapping
            mapping.put(key, node)
            
            // Add Node to front of list
            if (head == null) { // scenario for first node
                head = node
                last = node
            } else {
                head.next = node
                node.prev = head
                head = node
            }
            
            // increment the counter
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

    def printList(): Unit = {

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

/**
 * Your LRUCache object will be instantiated and called as such:
 * var obj = new LRUCache(capacity)
 * var param_1 = obj.get(key)
 * obj.put(key,value)
 */

object LRUCacheTest {
  def main(args: Array[String]): Unit = {
    val cache = new LRUCache(2)

    cache.put(2, 6)
    cache.printList()
    cache.put(1, 5)
    cache.printList()
    cache.put(1, 2)

    //cache.printList()

//    println(cache.get(1))
//    cache.put(3, 3)
//
//    cache.printList()
//
//    println(cache.get(2))
//    cache.put(4, 4)
//
//    cache.printList()

  }
}