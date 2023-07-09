package guide.atech.algorithms.team.router

import scala.collection.mutable

class BasicRouter extends Router {

  private val map = mutable.Map.empty[String, String]

  override def withRoute(path: String, result: String): Unit = this.synchronized {
    map.put(path, result)
  }

  override def route(path: String): String = this.synchronized {
    if (map.contains(path)) map(path)
    else ""
  }
}
