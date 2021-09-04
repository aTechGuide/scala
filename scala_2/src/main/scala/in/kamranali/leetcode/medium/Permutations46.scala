package in.kamranali.leetcode.medium

import scala.collection.immutable.{List, Queue}

object Permutations46 {
    // https://leetcode.com/problems/combination-sum/discuss/16502/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)
    def permute(nums: Array[Int]): List[List[Int]] = {

        val len = nums.length
        val temp = Array.fill[Boolean](len)(false)
        var res: List[List[Int]] = List()

        def util(selected: Array[Boolean], tempSol: Queue[Int]): Unit = {

            if (tempSol.length == len) {
                res = res :+ tempSol.toList
            } else {
                for (choice <- 0 until len) {
                    val elem = nums(choice)

                    if (!selected(choice)) {
                        selected(choice) = true

                        util(selected, tempSol :+ elem)
                        selected(choice) = false
                    }
                }
            }
        }

        util(temp, Queue())
        res
    }

    def main(args: Array[String]): Unit = {
        println(permute(Array(1, 2, 3, 4)))
    }
}