package in.kamranali.leetcode.medium

object MinCostToConnectAllPoints1584 {
  def minCostConnectPoints(points: Array[Array[Int]]): Int = {
    import scala.collection.mutable.PriorityQueue
    case class Node(v: Int, w: Int)
    
    //Initializations
    val vertices = points.length
    val dist = Array.fill[Int](vertices)(Int.MaxValue)
    val mstIncluded = Array.fill[Boolean](vertices)(false)
    
    val ordering = Ordering.fromLessThan[Node]((a, b) => b.w < a.w)
    val minQueue = PriorityQueue[Node]()(ordering)
    
    dist(0) = 0
    minQueue.enqueue(Node(0, dist(0)))
    // mst(0) = true // No Need, When we dequeue we mark it as processed
    
    var sum = 0
    
    // Auxiliary Function
    def manhattanDistance(p: Int, q: Int): Int = {
      math.abs(points(p)(0) - points(q)(0)) + math.abs(points(p)(1) - points(q)(1))
    }
    
    def minCost(): Unit = {
      if (minQueue.isEmpty) () // Distance array is finalized
      else {
        val elem = minQueue.dequeue
        
        if (mstIncluded(elem.v)) minCost() // Ignore this node as it will form Cycle
        else {
          // process node
          mstIncluded(elem.v) = true
          sum = sum + dist(elem.v)

          val children = (0 until vertices).toSet - elem.v
          children.foreach { child => {
            val distanceElemAndChild = manhattanDistance(elem.v, child)
            if (!mstIncluded(child) && (dist(child) > distanceElemAndChild)) {
              dist(child) = distanceElemAndChild
              minQueue.enqueue(Node(child, distanceElemAndChild))
            }
          }}

          minCost()
        }
      }
    }
    
    // Driver
    minCost()
    sum
  }

  def main(args: Array[String]): Unit = {
    println(minCostConnectPoints(Array(
      Array(0,0),Array(2,2),Array(3,10),Array(5,2), Array(7,0)
    )))
  }
}