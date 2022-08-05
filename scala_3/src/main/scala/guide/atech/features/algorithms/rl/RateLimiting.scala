package guide.atech.features.algorithms.rl

trait RateLimiting {
  def allowRequest(customerId: Int): Boolean
}
