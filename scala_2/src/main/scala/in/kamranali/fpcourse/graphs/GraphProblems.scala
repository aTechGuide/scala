package in.kamranali.fpcourse.graphs

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

  assert(outDegree(socialNetwork, "Alice") == 3)
  assert(inDegree(socialNetwork, "David") == 2)


}
