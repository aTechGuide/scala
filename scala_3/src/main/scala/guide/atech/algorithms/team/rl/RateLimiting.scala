package guide.atech.algorithms.team.rl

trait RateLimiting[T, U] {
  def isAllowed(customerId: T): U
}
