package guide.atech.algorithms.rl

import guide.atech.algorithms.rl.data.CustomerLimitsWithCredits

class TokenBucketWithCredits(customerDetails: CustomerDetailsWithCredits) extends RateLimiting[Int, Boolean] {

  def allowRequest(customerId: Int): Boolean = {

    customerDetails.memoryWithCredits.get(customerId) match {
      case Some(customer) =>
        // Step 1: Try Refilling
        refill(customer)

        // Step 2: Decrease Tokens
        decreaseTokens(customer)
      case None => true
    }
  }

  private def decreaseTokens(customer: CustomerLimitsWithCredits) = {
    if (customer.availableTokens > 0 || customer.credit > 0) {
      if (customer.availableTokens > 0) {
        customer.availableTokens = customer.availableTokens - 1
      } else if (customer.credit > 0) {
        customer.credit = customer.credit - 1
      }

      println(s"New limits $customer")
      true
    } else false
  }

  private def refill(customer: CustomerLimitsWithCredits): Unit = {
    val now = System.currentTimeMillis()
    val elapsedTime = now - customer.lastRefillTimeStamp
    val tokensToBeAdded = (elapsedTime / 1000) * customer.refillRate

    if (tokensToBeAdded > 0) {
      val actualAddition = math.min(customer.maxCapacity, customer.availableTokens + tokensToBeAdded).toInt
      val residual = tokensToBeAdded - (customer.availableTokens - actualAddition)

      customer.availableTokens = actualAddition
      customer.lastRefillTimeStamp = now

      customer.credit = math.min(customer.maxCredit, customer.credit + residual).toInt

      println(s"Tokens to be added = $tokensToBeAdded and actual addition = $actualAddition")
    }
  }
}
