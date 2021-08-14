package in.kamranali.leetcode.medium

/*
    Ref
    - https://www.youtube.com/watch?v=mouCn3CFpgg
 */

object LongestIncreasingSubsequence300 {
    def lengthOfLIS(nums: Array[Int]): Int = {

        val increasingSubsequence = Array.fill[Int](nums.length)(1)

        def adjust(j: Int, i: Int, max: Int): Int = {
            if (j == i) max
            else {
                if (nums(j) < nums(i) && increasingSubsequence(j) >= increasingSubsequence(i)) {
                    increasingSubsequence(i) = increasingSubsequence(j) + 1
                    val newMax = math.max(increasingSubsequence(i), max)
                    adjust(j + 1, i, newMax)
                } else adjust(j + 1, i, max)
            }
        }

        def findList(i: Int, max:Int): Int = {
            if (i == nums.length) max
            else {
                val newMax = adjust(0, i, max)
                findList(i + 1, newMax)
            }
        }

        val ans = findList(1, 1)
        println(increasingSubsequence.mkString(" "))
        ans
    }

    def main(args: Array[String]): Unit = {
        println(lengthOfLIS(Array(1,3,6,7,9,4,10,5,6)))
    }
}