package guide.atech.algorithms.team.rl

trait RateLimiting[I, R] {
  def isAllowed(customer: I): R
}
