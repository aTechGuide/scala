package in.kamranali.algorithms.greedy

/**
  * https://www.interviewbit.com/problems/seats/
  */

class Seats {

  def seats(A: String): Int  = {

    // Editorial Solution
    // Create an array of index containing x
    val aa = A.zipWithIndex.filter(t2 => t2._1 == 'x').map(_._2)

    if (aa.isEmpty) 0 // No x No movement
    else {
      val mid = aa.size / 2
      val mi = aa(mid)

      var ans = 0L
      var em = 0

      // Fix left side elements
      em = mi
      for (i <- mi to 0 by -1) {
        if (A(i) == 'x') {
          ans += em - i
          em -= 1
        }
      }

      // Fix right side elements
      em = mi + 1
      for (i <- mi + 1 until A.length) {
        if (A(i) == 'x') {
          ans += i - em
          em += 1
        }
      }

      (ans % 10000003).toInt
    }
  }

}

object Seats extends App {
  val sol = new Seats()

  // Test 1
  var A = ".x..x..x."
  var data = sol.seats(A)
  assert(data == 4)

  // Test 2
  A = "....x..xx...x.."
  data = sol.seats(A)
  assert(data == 5)

}
