package in.kamranali.leetcode.medium

object CourseSchedule207 {
  // DFS Based Finding Cycle
  def canFinish(numCourses: Int, prerequisites: Array[Array[Int]]): Boolean = {

    // Initial;izations
    val graph = Array.fill[List[Int]](numCourses)(List())

    // Build Graph
    prerequisites.foreach( node => {
      val parent = node(1)
      val child = node(0)

      graph(parent) = child :: graph(parent)
    })

    val visited = Array.fill[Boolean](numCourses)(false)

    // Auxiliary Function
    def dfs(v: Int, recStack: Array[Boolean]): Boolean = {

      visited(v) = true // Mark Node as visited
      recStack(v) = true
      val children = graph(v)

      val ans = children.foldLeft(false)((accum, vert) => {
        accum || recStack(vert) || dfs(vert, recStack)
      })

      recStack(v) = false
      ans
    }

    def isCycleExist(vertices: Int): Boolean = {

      (0 until vertices).foldLeft(false)((accum, node) => {

        val recArray = Array.fill[Boolean](numCourses)(false)
        accum || (if (visited(node)) false else dfs(node, recArray))
      })
    }

    // Driver
    !isCycleExist(numCourses)
  }

  def canFinish1(numCourses: Int, prerequisites: Array[Array[Int]]): Boolean = {

    // Auxiliary Function
    def sort(vertices: Int, edges: Array[Array[Int]]): List[Int] = {

      import scala.collection.mutable

      val graph = Array.fill[List[Int]](vertices)(List())
      val inDegree = Array.fill(vertices)(0)

      // Build Graph
      edges.foreach( node => {
        val parent = node(1)
        val child = node(0)

        graph(parent) = child :: graph(parent)
        inDegree(child) = inDegree(child) + 1
      })

      // Sources with 0 InDegree
      val queue = mutable.Queue[Int](inDegree.zipWithIndex.filter(value => {
        value._1 == 0
      }).map(_._2):_*)

      // Auxiliary Function
      // For each source, add it to the sortedOrder and subtract one from all of its children's in-degrees
      // if a child's in-degree becomes zero, add it to the sources queue
      def util(q: mutable.Queue[Int], res: List[Int]): List[Int] = {
        if (q.isEmpty) res
        else {
          val top = q.dequeue()
          val children = graph(top)

          // Adjust the InDegree and calculate newCandidates
          val newCandidates = children.foldLeft(List.empty[Int])((source, child) => {

            val newValue = inDegree(child) - 1
            inDegree(child) = newValue

            if (newValue == 0) source :+ child else source
          })

          newCandidates.foreach(elem => q.enqueue(elem))
          util(q, res :+ top)
        }
      }

      val sortedOrder = util(queue, List())

      if (sortedOrder.length != vertices) List() // topological sort is not possible as the graph has a cycle
      else sortedOrder
    }
    
    // Driver
    val sorted = sort(numCourses, prerequisites)
    if (sorted.isEmpty) false
    else true
  }

  def main(args: Array[String]): Unit = {
    println(canFinish(5, Array(Array(1, 4), Array(2, 4), Array(3, 1), Array(3, 2))))
  }
}