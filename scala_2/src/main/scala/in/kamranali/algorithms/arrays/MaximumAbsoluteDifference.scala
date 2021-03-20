package in.kamranali.algorithms.arrays

/**
  * https://www.interviewbit.com/problems/maximum-absolute-difference/
  */

class MaximumAbsoluteDifference {

  def maxArr(A: Array[Int]): Int  = {

    import Math._

    val length = A.length
    if (length == 1) return 0


    val temp1 = new Array[Int](length)
    val temp2 = new Array[Int](length)

    (0 until length).foreach{ i =>
      temp1(i) = A(i) + i
      temp2(i) = A(i) - i
    }

    val res = max(temp1.max - temp1.min, temp2.max - temp2.min)
    res
  }

  def maxArrSpaceOptimize(A: Array[Int]): Int  = {
    var max1 = Int.MinValue
    var max2 = Int.MinValue
    var min1 = Int.MaxValue
    var min2 = Int.MaxValue
    for {
      i <- A.indices
      ai = A(i)
    } {
      max1 = math.max(max1, ai + i)
      max2 = math.max(max2, ai - i)
      min1 = math.min(min1, ai + i)
      min2 = math.min(min2, ai - i)
    }
    math.max(math.max(0, max2 - min2), max1 - min1)
  }

}

object MaximumAbsoluteDifference extends App {
  val sol = new MaximumAbsoluteDifference()

  // Test 1
  var A = Array[Int](1, 3, -1)
  var data = sol.maxArr(A)
  assert(data == 5)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.maxArr(A)
//  assert(data == 42)

}
