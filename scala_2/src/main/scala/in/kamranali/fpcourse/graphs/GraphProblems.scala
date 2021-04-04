package in.kamranali.fpcourse.graphs

import scala.annotation.tailrec

object GraphProblems extends App {

  // Every Node will be a Key in the Map
  // For each Node, We keep a Set of other Nodes that this Node is associated to
  type Graph[T] = Map[T, Set[T]]

  // Example
  val socialNetwork: Graph[String] = Map(
    "Alice" -> Set("Bob", "Charlie", "David"),
    "Bob" -> Set(),
    "Charlie" -> Set("David"),
    "David" -> Set("Bob", "Mary"),
    "Mary" -> Set("Bob", "Charlie")
  )

  /**
    * EASY Problems
    */
  // Number of nodes that this node is associated (adjacent) to
  def outDegree[T](graph: Graph[T], node: T): Int = graph.getOrElse(node, Set()).size

  // number of nodes connected to `node`
  def inDegree[T](graph: Graph[T], node: T): Int = graph.values.count(_.contains(node))

  /**
    * Medium Problems
    */

  def isPath[T](graph: Graph[T], start: T, end: T): Boolean = {

    /*
      Alice -> Mary
      ipt([Alice], []) =
      ipt([Bob, Charlie, David], [Alice]) =
      ipt([Charlie, David], [Bob, Alice]) =
      ipt([David, David], [Charlie, Bob, Alice]) =
      ipt([David, Bob, Mary], [David, Charlie, Bob, Alice]) =
      ipt([Bob, Mary], [David, Charlie, Bob, Alice]) =
      ipt([Mary], [David, Charlie, Bob, Alice]) =
      = true
      N nodes, E edges
      Complexity: O(E)
     */

    @tailrec
    def isPathTailrec(remaining: List[T], consideredNodes: Set[T]): Boolean = {
      if (remaining.isEmpty) false
      else {
        val node = remaining.head

        if (node == end) true
        else if (consideredNodes.contains(node)) isPathTailrec(remaining.tail, consideredNodes)
        else isPathTailrec(remaining.tail ++ graph(node), consideredNodes + node)
      }
    }

    isPathTailrec(List(start), Set())
  }

  assert(outDegree(socialNetwork, "Alice") == 3)
  assert(inDegree(socialNetwork, "David") == 2)

  assert(isPath(socialNetwork, "Alice", "Mary"))
  assert(!isPath(socialNetwork, "Bob", "Mary"))


}
