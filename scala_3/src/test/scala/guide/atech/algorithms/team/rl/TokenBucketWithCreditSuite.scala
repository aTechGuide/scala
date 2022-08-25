package guide.atech.algorithms.rl

import guide.atech.algorithms.team.rl.{CustomerDetailsWithCredits, TokenBucketWithCredits}
import guide.atech.algorithms.team.rl.data.{CustomerLimits, CustomerLimitsWithCredits}

class TokenBucketWithCreditSuite extends munit.FunSuite {

  test("customer is rate limited correctly") {

    val testDataWithCredit = Map(
      1 -> CustomerLimitsWithCredits(1, 1, System.currentTimeMillis(), 1, 1, 0, 1)
    )

    val customerDetails = new CustomerDetailsWithCredits {
      override def memoryWithCredits: Map[Int, CustomerLimitsWithCredits] = testDataWithCredit
    }

    val tb = new TokenBucketWithCredits(customerDetails)

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
        assertEquals(tb.allowRequest(1), true)
      else
        assertEquals(tb.allowRequest(1), false)
    }}
  }

}