package in.kamranali.algorithms.arrays

/**
  * https://www.interviewbit.com/problems/max-sum-contiguous-subarray/
  *
  * References
  * - https://www.youtube.com/watch?v=2MmGzdiKR9Y
  */
class MaxSumContiguousSubArray {

  def maxSubArray(A: Array[Int]): Int  = {

    var sum = 0
    var maxSum = A.head

    for ( elem <- A) {
      sum += elem

      maxSum = Math.max(maxSum, sum)
      sum = Math.max(sum, 0) //<- If sum is < 0 reset to Zero`
    }

    maxSum
  }

  def maxSubArrayWithIndex(A: Array[Int]): (Int, Int, Int)  = {

    var sum = 0
    var maxSum = A.head

    var L = 0
    var R = 0

    var tempL = 0

    A.indices.foreach { idx =>

      sum += A(idx)

      if (sum >= 0) {
        if (sum > maxSum) {
          maxSum = sum
          L = tempL
          R = idx
        }
      } else {
        tempL = idx + 1
        sum = 0
      }
    }

    (maxSum, L, R)
  }

}

object MaxSumContiguousSubarrayTest extends App {


  val sol = new MaxSumContiguousSubArray()

  // Test 1
  var A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
  var data = sol.maxSubArray(A)
  assert(data == 6)

  // Test 2
  A = Array[Int](1, 2, 3, 4, -10)
  data = sol.maxSubArray(A)
  assert(data == 10)

  // Test 3
  A = Array[Int](-500)
  data = sol.maxSubArray(A)
  assert(data == -500)
}

object MaxSumContiguousSubarrayWithIndexTest extends App {


  val sol = new MaxSumContiguousSubArray()

  // Test 1
  var A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
  var data = sol.maxSubArrayWithIndex(A)
  assert(data == (6,3,6))

  A = Array[Int](1, 2, 3, 4, -10)
  data = sol.maxSubArrayWithIndex(A)
  assert(data == (10, 0, 3))
}
