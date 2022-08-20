package guide.atech.algorithms.router

trait Router[T] {
  def router(route: T): Unit
}
