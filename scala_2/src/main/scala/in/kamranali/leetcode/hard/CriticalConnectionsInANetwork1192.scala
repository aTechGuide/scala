package in.kamranali.leetcode.hard

object CriticalConnectionsInANetwork1192 {
  /*
    Tarjans Algorithm
    - Best Explaination - https://www.youtube.com/watch?v=Rhxs4k6DyMM
    
    Striver
    - https://www.youtube.com/watch?v=2rjZH0-2lhk
    - Code is written from Striver 
    - https://github.com/striver79/StriversGraphSeries/blob/main/bridgesJava
  */
  def criticalConnections(n: Int, connections: List[List[Int]]): List[List[Int]] = {
    // Data Structures
    case class Tracker(var timer: Int) {
      def incrementTimer(): Unit = {
        timer = timer + 1
      }
    }
    
    val graph = Array.fill[Set[Int]](n)(Set())
    
    // Fill the adjacency list
    connections.foreach { edge => {
      val u = edge.head
      val v = edge(1)
      
      // As edges are undirected
      graph(u) = graph(u) + v
      graph(v) = graph(v) + u
    }}
    
    // Define data structures
    val discovery = Array.ofDim[Int](n) // Time of Discovery for a node
    val low = Array.ofDim[Int](n) // Lowest Time
    val tracker = Tracker(1) // To assign the time of insertion
    
    var ans = List.empty[List[Int]]
    
    // Auxiliary Function
    def dfs(node: Int, parent: Int): Unit = {
      
      discovery(node) = tracker.timer
      low(node) = tracker.timer
      
      // Move Tracker
      tracker.incrementTimer()
      
      val children = graph(node)
      children.foreach { child => {
        if (child == parent) () // Ignore the parent - child edge
        else if (discovery(child) == 0) { // Implies child isn't visited
          
          // do DFS
          dfs(child, node)
          
          // When returning from DFS, adjust the low of node
          // Thought process -> The subgraph rooted at child, might be having a back edge to ancestor of node or to node itself
          low(node) = math.min(low(node), low(child))
          
          // look for a possibility of bridge
          if (low(child) > discovery(node)) {
            // If this condition is true then it implies there is NO backedge from subgraph of child to ancesor of node or to node itself
            ans = List(child, node) :: ans
          }
          
        } else {
          // This is a back edge, which can never be bridge
          low(node) = math.min(low(node), discovery(child))
          
        }
      }}
      
    }
    
    // Do DFS for thee graph
    (0 until n).foreach { vertex => {
      if (discovery(vertex) == 0) dfs(vertex, -1) // discovery(vertex) == 0 => Condition that node isn't visited
    }}
    
    // return ans
    ans
        
  }
}