package in.kamranali.leetcode.medium

object NetworkDelayTime743 {
  /*
    References
    - Striver
      - https://www.youtube.com/watch?v=jbhuqIASjoM
      - https://github.com/striver79/StriversGraphSeries/blob/main/djisktraJava
    - Avoiding Processed Nodes + keeping Track of Parent
      - https://www.youtube.com/watch?v=t2d-XYuPfg0 [Code]
      
      Time = O(E * LogV) as we traversed E edges and LogV for Queue Operation
  */
  case class Node(v: Int, edgeWeight: Int)
  case class NodeDist(v: Int, d: Int)
  
  def networkDelayTime(times: Array[Array[Int]], n: Int, k: Int): Int = {
    import scala.collection.mutable.PriorityQueue
    
    // Initializations
    val graph = Array.fill[List[Node]](n)(List())
    
    // Build Graph 
    times.foreach { elem => {
      graph(elem(0) - 1) = Node(elem(1) - 1, elem(2)) :: graph(elem(0) - 1)
    }}
    
    // Distance Array to keep track of distances from the source Node
    val dist = Array.fill[Int](n)(Int.MaxValue)
    dist(k - 1) = 0 // Distance of Source Node is zero
    
    // Keeping track of nodes that are processed
    val processed = Array.fill[Boolean](n)(false)
    
    val ordering = Ordering.fromLessThan[NodeDist]((a, b) => b.d < a.d)
    val minHeap = PriorityQueue[NodeDist]()(ordering)
    minHeap.enqueue(NodeDist(k - 1, 0)) // Add the min distance (i.e. Source Node). We begin processing from this node
    
    // Auxialiary Function
    def calcDelay(): Int = {
      if (minHeap.isEmpty) 
        if (dist.contains(Int.MaxValue)) -1 // i.e. there is a node that can't be visited from source
        else dist.max
      else {
        val nodeDist = minHeap.dequeue
        
        if (processed(nodeDist.v)) calcDelay() // This nodeDist is already processed so skip it
        else {
          processed(nodeDist.v) = true
          val childred = graph(nodeDist.v)

          //Process Childred while avoiding children that are already processed
          childred.foreach {child => {
            if (!processed(child.v) && (dist(nodeDist.v) + child.edgeWeight < dist(child.v))) {
              dist(child.v) = dist(nodeDist.v) + child.edgeWeight

              minHeap.enqueue(NodeDist(child.v, dist(child.v)))

              // Keep Track of newMax
              // newMax = math.max(newMax, dist(child.v)) // DON't Do THIS
            }
          }}

          calcDelay()
        }
      }
    }
    
    calcDelay()
  }
}