package in.kamranali.fpcourse.math

import scala.annotation.tailrec

/*
  Some rational numbers are infinite

  Print Standard representation of recurring decimals
  e.g.
  - 1/3 = 0.3333 = 0.(3)
  - 4/2 = 2
  - 1/6 = 0.1(6)
  - 1/333 = 0.(003) = 0.003003...
  - 1/2003 = 0.(very large set of recurring decimals)

 */
object P4RecurringDecimalsProblemHard {

  @tailrec
  def findRecurrenceStart(digit: Long, digits: List[Long], rem: Long, remainders: List[Long], currentIdx: Int): Int = {
    if (digits.isEmpty || remainders.isEmpty) -1
    else if (digit == digits.head && rem == remainders.head) currentIdx
    else findRecurrenceStart(digit, digits.tail, rem, remainders.tail, currentIdx + 1)

  }

  /*
        1/3 = fdt(1, 3, [], []) { quot = 3, rem = 1 }
        = fdt(1, 3, [3], [1]) { quot = 3, rem = 1 } <-- recurring set of decimals starts HERE
        = fdt(1, 3, [3, 3], [1, 1]) { quot = 3, rem = 1 }
          ... recurring set of decimals
        1/6 = fdt(1, 6, [], []) { quot = 1, rem = 4 }
        = fdt(4, 6, [1], [4]) { quot = 6, rem = 4 }
        = fdt(4, 6, [1,6], [4,4]) { quot = 6, rem = 4 } <-- recurring decimals start here
                       ^
        1/333 = fdt(1, 333, [], []) { quot = 0, rem = 10 }
        = fdt(10, 333, [0], [10]) { quot = 0, rem = 100 }
        = fdt(100, 333, [0,0], [10, 100] { quot = 3, rem = 1 }
        = fdt(1, 333, [0,0,3], [10, 100, 1]) { quot = 0, rem = 10 } <-- recurring decimals set
       */
  @tailrec
  def fractionDecimalTailrec(num: Long, denom: Long, quotients: List[Long], remainders: List[Long]): String = {
    val quotient = (num * 10) / denom
    val rem = (num * 10) % denom

    if (rem == 0) (quotients :+ quotient).mkString("")
    else {
      val recurrenceStartIndex = findRecurrenceStart(quotient, quotients, rem, remainders, 0)

      if (recurrenceStartIndex == -1) fractionDecimalTailrec(rem, denom, quotients :+ quotient, remainders :+ rem)
      else {
        val (beforeRecurrence, recurrence) = quotients.splitAt(recurrenceStartIndex)
        s"${beforeRecurrence.mkString("")}(${recurrence.mkString("")})"
      }
    }

  }

  // main Function
  def fractionToRecurringDecimals(numerator: Int, denominator: Int): String = {

    def f2d(n: Long, d: Long): String = {

      if (n > 0 && d < 0) s"-${f2d(n, -d)}"
      else if (n < 0 && d > 0) s"-${f2d(-n, d)}"
      else {
        val quotient = n / d
        val remainder = n % d

        if (remainder == 0) s"$quotient"
        else s"$quotient.${fractionDecimalTailrec(remainder, d, List(), List())}"

      }
    }

    f2d(numerator, denominator)
  }
}

object P4RecurringDecimalsProblemHardTest extends App {
  import P4RecurringDecimalsProblemHard._

  println(fractionToRecurringDecimals(1, 3))
  println(fractionToRecurringDecimals(1, 2))
  println(fractionToRecurringDecimals(4, 2))
  println(fractionToRecurringDecimals(1, 6))
  println(fractionToRecurringDecimals(1, 333))
  println(fractionToRecurringDecimals(1, 7))
  println(fractionToRecurringDecimals(1, 2003))
  println(fractionToRecurringDecimals(-1, 2))
  println(fractionToRecurringDecimals(1, Int.MinValue))

}