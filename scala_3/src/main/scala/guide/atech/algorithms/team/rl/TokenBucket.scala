package guide.atech.algorithms.team.rl

import guide.atech.algorithms.team.rl.data.CustomerLimits

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
class TokenBucket(customerDetails: Map[Int, CustomerLimits]) extends RateLimiting[Int, Boolean] {

  def isAllowed(customerID: Int): Boolean = {

    customerDetails.get(customerID) match
      case Some(customer) =>

        // Refill the quota after
        val now = System.currentTimeMillis()
        val elapsedInSec = (now - customer.time) / 1000
        val quotaToAdd = elapsedInSec * customer.rate
        println(s"Elapsed time in sec = $elapsedInSec and quotaAdded = $quotaToAdd")

        if (quotaToAdd > 0) {
          // Update quota and time
          val actualQuota = math.min(customer.rate, customer.quota + quotaToAdd.toInt) // Check
          customer.quota = actualQuota
          customer.time = now
        }

        // decrease the quota (if available)
        decreaseTokens(customer)
      case None => false
  }

  private def decreaseTokens(customer: CustomerLimits) = {
    if (customer.quota > 0) {
      println(s"Current Quota ${customer.quota}")
      customer.quota = customer.quota - 1
      println(s"Remaining Quota ${customer.quota}")
      true
    } else {
      println("Insufficient Quota Failing the request")
      false
    }
  }
}
