package guide.atech.algorithms.team.router

trait Router {
  def withRoute(path: String, result: String): Unit
  def route(path: String): String
}
