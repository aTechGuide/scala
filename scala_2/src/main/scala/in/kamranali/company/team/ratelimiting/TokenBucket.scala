package in.kamranali.company.team.ratelimiting

class TokenBucket(capacity: Long, refillRate: Long) extends RateLimiting {

  private var availableTokens = capacity
  private var lastRefillTimeStamp = System.currentTimeMillis()

  def allowRequest(customerId: Int): Boolean = {
    // Step 1: Try Refilling
    refill()

    if (availableTokens > 0) {
      availableTokens = availableTokens - 1
      true
    } else false
  }

  private def refill(): Unit = {
    val now = System.currentTimeMillis()
    val elapsedTime = now - lastRefillTimeStamp
    val tokensToBeAdded = (elapsedTime / 1000) * refillRate

    if (tokensToBeAdded > 0) {
      availableTokens = math.max(capacity, availableTokens + tokensToBeAdded)
      lastRefillTimeStamp = now
    }
  }
}
