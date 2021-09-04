package in.kamranali.leetcode.medium

import scala.collection.immutable.Queue

// https://leetcode.com/problems/combination-sum/discuss/16502/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)
object PermutationsII47 {
    def permuteUnique(nums: Array[Int]): List[List[Int]] = {

        val len = nums.length
        val sorted = nums.sorted
        val temp = Array.fill[Boolean](len)(false)
        var res: List[List[Int]] = List()

        def util(selected: Array[Boolean], tempSol: Queue[Int]): Unit = {

            if (tempSol.length == len) {
                res = res :+ tempSol.toList
            } else {
                for (choice <- 0 until len) {
                    val elem = sorted(choice)

                    if (!selected(choice) && !(choice > 0 && !selected(choice - 1) && sorted(choice - 1) == sorted(choice))) {
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
        println(permuteUnique(Array(1, 1, 2)))
    }
}