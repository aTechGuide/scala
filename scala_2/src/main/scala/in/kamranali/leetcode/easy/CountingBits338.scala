package in.kamranali.leetcode.easy

/*
  https://leetcode.com/problems/counting-bits/
 */

/*
  DP Solution -> https://leetcode.com/problems/counting-bits/discuss/79557/How-we-handle-this-question-on-interview-Thinking-process-%2B-DP-solution

      dp(0) = 0
      dp(1) = dp(0) + 1 = 1     = dp(1 - 1) + 1
      dp(2) = dp(0) + 1 = 1     = dp(2 - 2) + 1
      dp(3) = dp(1) + 1 = 2     = dp(3 - 2) + 1
      dp(4) = dp(0) + 1 = 1     = dp(4 - 4) + 1
      dp(5) = dp(1) + 1 = 2     = dp(5 - 4) + 1
      dp(6) = dp(2) + 1 = 2     = dp(6 - 4) + 1
      dp(7) = dp(3) + 1 = 3     = dp(7 - 4) + 1

 */

object CountingBits338 {

  def countBitsBF(n: Int): Array[Int] = {

    import scala.collection.immutable.Queue

    def countBitsUtil(remaining: Int, acc: Int = 0): Int = {
      if (remaining == 0) acc
      else {
        val isLastBitOne = (remaining & 1) == 1

        if (isLastBitOne)
          countBitsUtil(remaining >> 1, acc + 1)
        else
          countBitsUtil(remaining >> 1, acc)

      }
    }

    def countTailrec(ctr: Int, res: Queue[Int]): Array[Int] = {

      if (ctr == n + 1) res.toArray[Int]
      else {
        val bitsCount = countBitsUtil(ctr)
        countTailrec(ctr + 1, res :+ bitsCount)
      }
    }

    if (n == 0) Array(0)
    else countTailrec(1, Queue(0))

  }

  def countBits(n: Int): Array[Int] = {

    val memory = Array.fill[Int](n + 1)(0)

    def util(idx: Int, memory: Array[Int], offset: Int = 1): Array[Int] = {

      if (idx == n + 1) memory
      else {
        val newOffset = if (offset * 2 == idx) offset * 2 else offset

        memory(idx) = memory(idx - newOffset) + 1
        util(idx + 1, memory, newOffset)
      }
    }

    util(1, memory)

  }

  def main(args: Array[String]): Unit = {

//    assert(countBits(0) sameElements Array(0))
//    assert(countBits(1) sameElements Array(0,1))
//    assert(countBits(2) sameElements Array(0,1,1))

    println(countBits(2).mkString(" "))
//    assert(countBits(3) == 2)
//    assert(countBits(4) == 1)
  }

}
