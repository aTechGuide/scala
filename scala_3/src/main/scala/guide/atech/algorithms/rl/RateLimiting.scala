package guide.atech.algorithms.rl

trait RateLimiting[T, U] {
  def allowRequest(customerId: T): U
}
