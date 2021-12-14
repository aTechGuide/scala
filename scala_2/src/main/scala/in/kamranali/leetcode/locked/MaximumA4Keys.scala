package in.kamranali.leetcode.locked

object MaximumA4Keys {

  def maxA(N: Int): Int = {

    if (N <= 6) N
    else {
      var ans = 0

      (N - 3 to 1 by -1).foreach( b => {
        val tempAns = (N - b - 1) * maxA(b) // <--- Optimize this recursive F(x) by using memory
        ans = ans max tempAns
      })

      ans
    }
  }

  def maxADP(N: Int): Int = {

    if (N <= 6) N
    else {
      // Create Memory
      val memory = Array.fill[Int](N)(0)

      // Base Condition, N <= 6
      (1 to 6).foreach( idx => {
        memory(idx - 1) = idx
      })

      // Compute the answer for each
      (7 to N).foreach(n => {

        (n - 3 to 1 by -1).foreach(b => {
          val tempAns = (n - b - 1) * memory(b - 1)
          memory(n-1) = memory(n-1) max tempAns
        })
      })

      // return answer
      // println(memory.mkString(" "))
      memory(N - 1)
    }
  }

  def main(args: Array[String]): Unit = {
    assert(maxADP(7) == 9)
    assert(maxADP(11) == 27)
  }
}
