package in.kamranali.fpcourse.math

object P4RecurringDecimalsProblemSimplerHard {

  import scala.collection.immutable.Queue
  def fractionDecimalTailrec(num: Long, denom: Long, quotients: Queue[Long], remMap: Map[Long, Int], idx: Int): String = {
    val quotient = (num * 10) / denom
    val rem = (num * 10) % denom
    val newQuotients = quotients :+ quotient

    if (rem == 0) newQuotients.mkString("")
    else {
      if (remMap.contains(rem)) {
        val recurrenceStartIndex = remMap(rem)

        val (beforeRecurrence, recurrence) = newQuotients.splitAt(recurrenceStartIndex + 1)
        s"${beforeRecurrence.mkString("")}(${recurrence.mkString("")})"
      } else {
        fractionDecimalTailrec(rem, denom, newQuotients, remMap + (rem -> idx), idx + 1)
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
        else s"$quotient.${fractionDecimalTailrec(remainder, d, Queue(), Map(), 0)}"

      }
    }

    f2d(numerator, denominator)
  }
}

object P4RecurringDecimalsProblemSimplerHardTest extends App {
  import P4RecurringDecimalsProblemSimplerHard._
  println(fractionToRecurringDecimals(1, 6))

}