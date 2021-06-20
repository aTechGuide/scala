package in.kamranali.leetcode.easy


object Numberof1Bits191 {

  def hammingWeightSol1(n: Int): Int = {

    val binaryString = Integer.toBinaryString(n)

    binaryString.foldLeft[Int](0)((acc, value) => if (value == '1') acc + 1 else acc)
  }

  def hammingWeight(n: Int): Int = {

    def count(remaining: Int, acc: Int = 0): Int = {
      if (remaining == 0) acc
      else {
        val isLastBitOne = (remaining & 1) == 1

        if (isLastBitOne)
          count(remaining >>> 1, acc + 1)
        else
          count(remaining >>> 1, acc)
      }
    }

    count(n)
  }

  def main(args: Array[String]): Unit = {


    println(hammingWeight(-3))

    // println(-1 >> 1)
  }



}
