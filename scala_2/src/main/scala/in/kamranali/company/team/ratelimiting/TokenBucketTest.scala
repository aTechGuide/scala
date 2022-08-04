package in.kamranali.company.team.ratelimiting

object TokenBucketTest {

  def main(args: Array[String]): Unit = {
    val tb = new TokenBucket(3, 3)

    (1 to 5).foreach { idx => {
      println(s"Request $idx is " + tb.allowRequest(1))
    }}

    println("Sleeping for a sec")
    Thread.sleep(1000)
    println("Waking up")

    (1 to 5).foreach { idx => {
      println(s"Request $idx is " + tb.allowRequest(1))
    }}
  }

}
