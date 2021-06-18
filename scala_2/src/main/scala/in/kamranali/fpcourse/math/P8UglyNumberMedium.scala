package in.kamranali.fpcourse.math

import scala.annotation.tailrec
import scala.collection.immutable.Queue

object P8UglyNumberMedium {

  // ugly = only the factors 2, 3 and 5
  // 1 is ugly
  // assume positive inputs
  // examples: 6, 25, 100
  // not ugly: 14, 39
  @tailrec
  def uglyNumber(number: Int): Boolean = {
    if (number == 1) true
    else if (number % 2 == 0) uglyNumber(number / 2)
    else if (number % 3 == 0) uglyNumber(number / 3)
    else if (number % 5 == 0) uglyNumber(number / 5)
    else false
  }

  // the nth ugly number, given the index
  // 1 is the first ugly number
  def nthUgly(n: Int): Int = {

    def min3(a: Int, b: Int, c: Int): Int = math.min(a, math.min(b, c))

    /*
     Complexity: O(N) time, O(N) space
    */
    @tailrec
    def nthUglyTailrec(index: Int, q2: Queue[Int], q3: Queue[Int], q5: Queue[Int]): Int = {
      val min = min3(q2.head, q3.head, q5.head)

      if (index == n) min
      else {
        val newQ2 = (if (min == q2.head) q2.tail else q2) :+ (min * 2)
        val newQ3 = (if (min == q3.head) q3.tail else q3) :+ (min * 3)
        val newQ5 = (if (min == q5.head) q5.tail else q5) :+ (min * 5)

        nthUglyTailrec(index + 1, newQ2, newQ3, newQ5)
      }
    }

    if (n == 1) 1
    else nthUglyTailrec(2, Queue(2), Queue(3), Queue(5))
  }

  def main(args: Array[String]): Unit = {
    println(uglyNumber(1)) // true
    println(uglyNumber(2)) // true
    println(uglyNumber(3)) // true
    println(uglyNumber(5)) // true
    println(uglyNumber(1200)) // true
    println(uglyNumber(7 * 2 * 2 * 3 * 5 * 2 * 5 * 3)) // false

    // nth ugly
    println((1 to 100).map(nthUgly).toList)
  }
}
