package guide.atech.algorithms.rl

import guide.atech.algorithms.rl.data.{CustomerLimits, CustomerLimitsWithCredits}

class TokenBucketSuite extends munit.FunSuite {

  test("customer is rate limited correctly") {

    val testData = Map(
      1 -> CustomerLimits(1, 1, System.currentTimeMillis(), 1, 1)
    )
    
    val customerDetails = new CustomerDetails {
      override def memory: Map[Int, CustomerLimits] = testData
    }
    val tb = new TokenBucket(customerDetails)

    (1 to 3).foreach { idx => {
      if (idx == 1)
        assertEquals(tb.allowRequest(1),true)
      else
        assertEquals(tb.allowRequest(1),false)
    }}

    println("Sleeping for a sec")
    Thread.sleep(1000)
    println("Waking up")

    (1 to 3).foreach { idx => {
      if (idx == 1)
        assertEquals(tb.allowRequest(1), true)
      else
        assertEquals(tb.allowRequest(1), false)
    }}
  }

}