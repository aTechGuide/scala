package in.kamranali.algorithms.arrays

/**
  * https://www.interviewbit.com/problems/max-sum-contiguous-subarray/
  */
class MaxSumContiguousSubarray {

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

}

object MaxSumContiguousSubarrayTest extends App {


  val sol = new MaxSumContiguousSubarray()

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
