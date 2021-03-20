package in.kamranali.algorithms.greedy

/**
  * https://www.interviewbit.com/problems/highest-product/
  */

class HighestProduct {

  def maxp3(A: Array[Int]): Int  = {

    if (A.length < 3) return -1

    var highest = math.max(A(0), A(1))
    var lowest = math.min(A(0), A(1))

    var highestP2 = A(0) * A(1)
    var lowestP2 = A(0) * A(1)

    var highest3 = A(0) * A(1) * A(2)

    (2 until A.length).foreach{ idx =>

      var curr = A(idx)
      highest3 = math.max(highest3, math.max(curr * highestP2, curr * lowestP2))

      highestP2 = math.max(highestP2, math.max(curr * highest, curr * lowest))
      lowestP2 = math.min(lowestP2, math.min(curr * highest, curr * lowest))

      highest = math.max(highest, curr)
      lowest = math.min(lowest, curr)
    }

    highest3
  }

}

object HighestProduct extends App {
  val sol = new HighestProduct()

  // Test 1
  var A = Array[Int](-65, 41, 15, -11, 69, 23, -63, -4, 49, -99, -61, 17, -61)
  var data = sol.maxp3(A)
  assert(data == 444015)

  // Test 1
//  A = Array[Int](1, 2, 3, 4)
//  data = sol.maxp3(A)
//  assert(data == 24)

  // Test 2
//  A = Array[Int](0, -1, 3, 100, 70, 50)
//  data = sol.maxp3(A)
//  assert(data == 350000)

}
