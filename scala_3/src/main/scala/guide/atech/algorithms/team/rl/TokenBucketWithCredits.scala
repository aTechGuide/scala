package guide.atech.algorithms.team.rl

import guide.atech.algorithms.team.rl.data.CustomerLimitsWithCredits

class TokenBucketWithCredits(customerDetails: Map[Int, CustomerLimitsWithCredits]) extends RateLimiting[Int, Boolean] {

  def isAllowed(customerId: Int): Boolean = {

    customerDetails.get(customerId) match {
      case Some(customer) =>
        // Step 1: Try Refilling
        val now = System.currentTimeMillis()
        val elapsedTimeInSec = (now - customer.time) / 1000
        val tokensToBeAdded = elapsedTimeInSec * customer.rate

        if (tokensToBeAdded > 0) {
          val actualAddition = math.min(customer.rate, customer.quota + tokensToBeAdded).toInt
          val residual = tokensToBeAdded - (customer.quota - actualAddition) // This is new and has to be calculated here

          customer.quota = actualAddition
          customer.time = now

          customer.credit = math.min(customer.maxCredit, customer.credit + residual).toInt

          println(s"Tokens to be added = $tokensToBeAdded and actual addition = $actualAddition")
        }

        // Step 2: Decrease Tokens
        decreaseTokens(customer)
      case None => true
    }
  }

  private def decreaseTokens(customer: CustomerLimitsWithCredits) = {
    if (customer.quota > 0 || customer.credit > 0) {
      if (customer.quota > 0) {
        customer.quota = customer.quota - 1
      } else {
        customer.credit = customer.credit - 1
      }

      println(s"New limits $customer")
      true
    } else false
  }
}
