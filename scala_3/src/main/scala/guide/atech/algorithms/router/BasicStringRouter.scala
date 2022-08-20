package guide.atech.algorithms.router

import guide.atech.algorithms.router.model.Task

class BasicStringRouter[T <: Task](routeMap: Map[String, T]) extends Router[String] {

  override def router(route: String): Unit = {
    routeMap.get(route) match
      case Some(value) => value.trigger(Map("" -> ""))
      case None => println("Didn't match Anything")
  }
}
