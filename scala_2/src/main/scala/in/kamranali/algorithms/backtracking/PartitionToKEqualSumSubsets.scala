package in.kamranali.algorithms.backtracking

/**
  * BT
  *   Time
  *     - Not Trivial to Calculate
  *   Space
  *     - O(n)
  *
  *
  * Ref
  * - https://www.youtube.com/watch?v=qpgqhp_9d1s
  * - https://leetcode.com/problems/partition-to-k-equal-sum-subsets/submissions/
  */

class PartitionToKEqualSumSubsets {

  def canPartitionKSubsets(nums: Array[Int], k: Int): Boolean = {

    // iterNums explanation
    // We will try to fill each bucket from 0 element.
    // But once we found the first element for that bucket, The next element for that bucket will be found in `idx + 1`
    def partitionUtil(iterNums: Int, nums: Array[Int], used: Array[Boolean], k: Int, inProgressBucketSum: Int, targetSum: Int): Boolean = {

      if (k == 1) true
      else if (inProgressBucketSum == targetSum) partitionUtil(0, nums, used, k - 1, 0, targetSum)
      else {
        (iterNums until nums.length).foreach { idx =>

          val tempSum = inProgressBucketSum + nums(idx)
          if (!used(idx) && tempSum <= targetSum) {
            // Choose
            used(idx) = true

            // Explore
            val res = partitionUtil(idx + 1, nums, used, k, tempSum, targetSum)
            if (res) return true

            // Unchoose
            used(idx) = false
          }
        }
        false
      }
    }

    val sumOfItems = nums.sum
    if (k == 0 || sumOfItems % k != 0) false
    else {
      val used = Array.fill[Boolean](nums.length)(false)
      val targetSum: Int = sumOfItems / k
      partitionUtil(0, nums, used, k, 0, targetSum)
    }

  }

}

object PartitionToKEqualSumSubsets extends App {
  val sol = new PartitionToKEqualSumSubsets()
  var A: Array[Int] = _
  var K: Int = _
  var data: Boolean = _

  // Test 1
  A = Array[Int](4, 3, 2, 3, 5, 2, 1)
  K = 4
  data = sol.canPartitionKSubsets(A, K)
  println(data)
  assert(data)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.solve(A)
//  assert(data == 42)

}