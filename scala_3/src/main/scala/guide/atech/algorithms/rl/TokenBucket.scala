package guide.atech.algorithms.rl

import guide.atech.algorithms.rl.data.CustomerLimits

/**
 * Created by Siddharth on 2/19/18.
 * This class is implemented based on Token bucket algorithm
 *
 * - https://www.linkedin.com/pulse/api-rate-limiting-using-token-bucket-algorithm-siddharth-patnaik/
 * - https://www.youtube.com/watch?v=FU4WlwfS3G0
 *//**
 * Created by Siddharth on 2/19/18.
 * This class is implemented based on Token bucket algorithm
 *
 * - https://www.linkedin.com/pulse/api-rate-limiting-using-token-bucket-algorithm-siddharth-patnaik/
 * - https://www.youtube.com/watch?v=FU4WlwfS3G0
 */
class TokenBucket(customerDetails: CustomerDetails) extends RateLimiting {

  def allowRequest(customerId: Int): Boolean = customerDetails.memory.get(customerId) match {
    case Some(customer) =>
      // Step 1: Try Refilling
      refill(customer)

      // Step 2: Decrease Tokens
      decreaseTokens(customer)
    case None => true
  }

  private def decreaseTokens(customer: CustomerLimits) = {
    if (customer.availableTokens > 0) {
      customer.availableTokens = customer.availableTokens - 1
      println(s"New limits $customer")
      true
    } else false
  }

  private def refill(customer: CustomerLimits): Unit = {
    val now = System.currentTimeMillis()
    val elapsedTime = now - customer.lastRefillTimeStamp
    val tokensToBeAdded = (elapsedTime / 1000) * customer.refillRate

    if (tokensToBeAdded > 0) {
      val actualAddition = math.min(customer.maxCapacity, customer.availableTokens + tokensToBeAdded).toInt
      customer.availableTokens = actualAddition
      customer.lastRefillTimeStamp = now

      println(s"Tokens to be added = $tokensToBeAdded and actual addition = $actualAddition")
    }
  }
}
