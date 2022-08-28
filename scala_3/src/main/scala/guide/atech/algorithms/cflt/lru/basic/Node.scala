package guide.atech.algorithms.cflt.lru.basic

case class Node(key: Int, var value: Int, var next: Option[Node] = None, var prev: Option[Node] = None)
