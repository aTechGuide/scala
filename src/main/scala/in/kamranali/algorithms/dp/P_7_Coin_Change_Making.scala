package in.kamranali.algorithms.dp

/**
  *
  * Solution in Notes
  *
  * Ref
  * - https://www.youtube.com/watch?v=jgiZlGzXMBw
  * - https://leetcode.com/problems/coin-change/
  */

class P_7_Coin_Change_Making {

  def coinChange(coins: Array[Int], amount: Int): Int = {

    val memory = Array.fill[Int](amount + 1)(amount + 1)
    memory(0) = 0


    (1 to amount).foreach { sum =>

      for (coin <- coins) {

        memory(sum) =
        if (coin <= sum) math.min(memory(sum), memory(sum - coin) + 1)
        else memory(sum)
      }

    }

    println(memory.mkString(" "))

    if (memory(amount) < amount + 1) memory(amount) else -1
  }

}

object P_7_Coin_Change_Making extends App {
  val sol = new P_7_Coin_Change_Making()
  var A: Array[Int] = _
  var data: Int = _

  // Test 1
  A = Array[Int](1, 2, 5)
  data = sol.coinChange(A, 11)
  assert(data == 3)

  // Test 2
  A = Array[Int](2)
  data = sol.coinChange(A, 3)
  assert(data == -1)

}
