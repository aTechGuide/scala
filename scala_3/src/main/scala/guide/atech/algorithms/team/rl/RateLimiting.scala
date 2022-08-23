package guide.atech.algorithms.team.rl

trait RateLimiting[T, U] {
  def allowRequest(customerId: T): U
}
