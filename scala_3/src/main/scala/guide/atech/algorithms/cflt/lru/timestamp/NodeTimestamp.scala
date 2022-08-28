package guide.atech.algorithms.cflt.lru.timestamp

case class NodeTimestamp(key: Int, var value: Int, var timestamp: Long, var next: Option[NodeTimestamp] = None, var prev: Option[NodeTimestamp] = None)
