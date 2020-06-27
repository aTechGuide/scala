package in.kamranali.algorithms.arrays

/**
  * https://www.interviewbit.com/problems/max-distance/
  */

class MaxDistance {

  def maximumGap(A: Array[Int]): Int  = {

    var len = A.length
    val lArr = Array.fill[Int](len)(A.head)
    val rArr = Array.fill[Int](len)(A.last)

    (1 until A.length).foreach { idx =>
      lArr(idx) = math.min(lArr(idx - 1), A(idx))
    }

    (A.length - 2 to 0 by -1).foreach { idx =>
      rArr(idx) = math.max(rArr(idx + 1), A(idx))
    }

    var i = 0
    var j = 0

    var max = -1

    while (i < len && j < len) {

      if (lArr(i) <= rArr(j)) {
        max = math.max(max, j - i)
        j += 1
      } else {
        i += 1
      }
    }

    max

  }

}

object MaxDistance extends App {
  val sol = new MaxDistance()

  // Test 1
  var A = Array[Int](3, 5, 4, 2)
  var data = sol.maximumGap(A)
  assert(data == 2)

  // Test 2
  A = Array[Int](5,4,3,2,1)
  data = sol.maximumGap(A)
  assert(data == 0)

  // Test 3
  A = Array[Int](100, 100, 100, 100, 100)
  data = sol.maximumGap(A)
  assert(data == 4)

}
