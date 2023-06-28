package guide.atech.algorithms.rl

import guide.atech.algorithms.team.rl.TokenBucketWithCredits
import guide.atech.algorithms.team.rl.data.{CustomerLimits, CustomerLimitsWithCredits}

class TokenBucketWithCreditSuite extends munit.FunSuite {

  test("customer is rate limited correctly") {
    val customerID = 1
    val rate = 1
    val initialQuota = 1
    val time = System.currentTimeMillis()

    // (id: Long, rate: Int, var time: Long, var quota: Int, var credit: Int, val maxCredit: Int)
    val customerLimitsWithCredits = Map(
      customerID -> CustomerLimitsWithCredits(customerID, rate, time, initialQuota, 0, 1)
    )

    val tb = new TokenBucketWithCredits(customerLimitsWithCredits)

//    (1 to 3).foreach { idx => {
//      if (idx == 1)
//        assertEquals(tb.allowRequest(1),true)
//      else
//        assertEquals(tb.allowRequest(1),false)
//    }}

    println("Sleeping for a sec")
    Thread.sleep(1000)
    println("Waking up")

    (1 to 3).foreach { idx => {
      if (idx <= 2)
        assertEquals(tb.isAllowed(1), true)
      else
        assertEquals(tb.isAllowed(1), false)
    }}
  }

}