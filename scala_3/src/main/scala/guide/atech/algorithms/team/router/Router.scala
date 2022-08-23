package guide.atech.algorithms.team.router

trait Router[T] {
  def router(route: T): Unit
}
