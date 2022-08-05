package guide.atech.algorithms.rl

trait RateLimiting {
  def allowRequest(customerId: Int): Boolean
}
