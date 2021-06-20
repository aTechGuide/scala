package in.kamranali.fpcourse.graphs

import scala.annotation.tailrec

object GraphProblems {

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

  def findPath[T](graph: Graph[T], start: T, end: T): List[T] = {
    /*
      Charlie -> Mary
      fpt([(Charlie, [Charlie])], []) =
        neighbors = [David]
        tuples = [(David, [David, Charlie])]

      fpt([(David, [David, Charlie])], [Charlie]) =
        neighbors = [Bob, Mary]
        tuples = [(Bob, [Bob, David, Charlie], (Mary [Mary, David, Charlie])]

      fpt([(Bob, [Bob, David, Charlie]), (Mary [Mary, David, Charlie])], [David, Charlie]) =
        neighbors = []
        tuples = []

      fpt([(Mary, [Mary, David, Charlie])], [David, Charlie, Bob]) =

      [Charlie, David, Mary]
     */

    @tailrec
    def findPathTailrec(remaining: List[(T, List[T])], consideredNodes: Set[T]): List[T] = {
      if (remaining.isEmpty) List()
      else {
        val (node, path) = remaining.head

        if (node == end) path.reverse
        else if (consideredNodes.contains(node)) findPathTailrec(remaining.tail, consideredNodes)
        else {
          val neighbours = graph(node)
          val tuples = neighbours.map(n => (n, n :: path))
          findPathTailrec(remaining.tail ++ tuples, consideredNodes + node)
        }
      }
    }

//    findPathTailrec(List((start, List(start))), Set())

    // Expanding findPathTailrec eagerly for findCycle Method
    findPathTailrec(graph(start).map(n => (n, n :: List(start))).toList, Set(start))

  }

  def findCycle[T](graph: Graph[T], node: T): List[T] =
    findPath(graph, node, node)

  def makeUndirected[T](graph: Graph[T]): Graph[T] = {
    // A -> B and B -> A
    def addEdge(graph: Graph[T], from: T, to: T): Graph[T] = {
      if (!graph.contains(from)) graph + (from -> Set(to))
      else {
        val neighbours = graph(from)
        graph + (from -> (neighbours + to))
      }
    }

    @tailrec
    def addOpposingEdges(remainingNodes: Set[T], acc: Graph[T]): Graph[T] = {
      if (remainingNodes.isEmpty) acc
      else {
        val node = remainingNodes.head
        val neighbours = graph(node)
        val newGraph = neighbours.foldLeft(acc)((accum, neighbour) => addEdge(accum, neighbour, node))
        addOpposingEdges(remainingNodes.tail, newGraph)
      }
    }

    addOpposingEdges(graph.keySet, graph)

  }

  def main(args: Array[String]): Unit = {

    def easyProblems(): Unit = {
      assert(outDegree(socialNetwork, "Alice") == 3)
      assert(inDegree(socialNetwork, "David") == 2)
    }

    def mediumProblems(): Unit = {
      assert(isPath(socialNetwork, "Alice", "Mary"))
      assert(!isPath(socialNetwork, "Bob", "Mary"))

      assert(findPath(socialNetwork, "Charlie", "Mary") == List("Charlie", "David", "Mary"))
      assert(findPath(socialNetwork, "Bob", "Mary") == List())

      assert(findCycle(socialNetwork, "Alice") == List())

      val undirectedNetwork = makeUndirected(socialNetwork)

      println(undirectedNetwork("Bob")) // Set(Alice, Mary, David)
      println(undirectedNetwork("Alice")) // Set(Bob, Charlie, David)
      println(undirectedNetwork("David")) // Set(Bob, Mary, Alice, Charlie)
    }



  }



}
