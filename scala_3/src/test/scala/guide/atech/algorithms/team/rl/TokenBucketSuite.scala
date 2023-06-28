package guide.atech.algorithms.team.rl

import guide.atech.algorithms.team.rl.TokenBucket
import guide.atech.algorithms.team.rl.data.{CustomerLimits, CustomerLimitsWithCredits}

class TokenBucketSuite extends munit.FunSuite {

  test("customer is rate limited correctly") {
    // c = 1, 2 rps, t=0 quota=2
    val customerID = 1
    val rate = 2
    val initialQuota = 2

    val customerDetails = Map((customerID, CustomerLimits(customerID, rate, System.currentTimeMillis(), initialQuota)))
    val rl = TokenBucket(customerDetails)

    println("First Request")
    assertEquals(true, rl.isAllowed(customerID))
    println("Second Request")
    assertEquals(true, rl.isAllowed(customerID))
    println("Third Request")
    assertEquals(false, rl.isAllowed(customerID))

    println("Waiting for 1s to refill the quota")
    Thread.sleep(1000)
    assertEquals(true, rl.isAllowed(customerID))

  }

}