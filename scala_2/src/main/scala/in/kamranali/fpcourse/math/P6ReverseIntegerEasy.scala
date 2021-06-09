package in.kamranali.fpcourse.math

import scala.annotation.tailrec

// return a number with the digits reversed
// if the result overflows Int, return 0
object P6ReverseIntegerEasy {

  def reverseInteger(number: Int): Int = {

    // assumes positive arguments
    @tailrec
    def revIntTailrec(remaining: Int, accum: Int): Int = {
      if (remaining == 0) accum
      else {
        val remainder = remaining % 10
        val tentativeRes = accum * 10 + remainder

        if ((accum >= 0) != (tentativeRes >= 0)) 0
        else revIntTailrec(remaining / 10, tentativeRes)
      }
    }

    // -2^31...2^31-1
    // Int.MinValue = 10000000000000000000000000000000
    // -Int.MinValue = 01111111111111111111111111111111 + 1 = 10000000000000000000000000000000 = Int.MinValue
    // -n = ~n + 1

    if (number == Int.MinValue) 0
    else if (number >= 0) revIntTailrec(number, 0)
    else -revIntTailrec(-number, 0)
  }

  def main(args: Array[String]): Unit = {
    // positives
    println("Positives:")
    println(reverseInteger(0))    // 0
    println(reverseInteger(9))    // 9
    println(reverseInteger(53))   // 35
    println(reverseInteger(504))  // 405
    println(reverseInteger(540))  // 45
    println(reverseInteger(53678534)) // 43587635
    println(reverseInteger(Int.MaxValue)) // 0
    // negatives
    println("Negatives:")
    println(reverseInteger(-9))     // -9
    println(reverseInteger(-53))    // -35
    println(reverseInteger(-504))   // -405
    println(reverseInteger(-540))   // -45
    println(reverseInteger(-53678534)) // -43587635
    println(reverseInteger(Int.MinValue)) // 0
  }

}
