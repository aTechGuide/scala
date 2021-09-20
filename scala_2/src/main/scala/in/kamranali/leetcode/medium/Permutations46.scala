package in.kamranali.leetcode.medium



object Permutations46 {
    // https://leetcode.com/problems/combination-sum/discuss/16502/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)
    def permute(nums: Array[Int]): List[List[Int]] = {
        import scala.collection.mutable.ArrayBuffer
        import scala.collection.immutable.{List, Queue}

        val len = nums.length
        val temp = Array.fill[Boolean](len)(false)

        def util(selected: Array[Boolean], tempSol: Queue[Int], res: ArrayBuffer[List[Int]]): Unit = {

            if (tempSol.length == len) {
                res.addOne(tempSol.toList)
            } else {
                for (choice <- 0 until len) {
                    if (!selected(choice)) {

                        // Choose
                        selected(choice) = true

                        // Recurse
                        val elem = nums(choice)
                        util(selected, tempSol :+ elem, res)

                        // Backtrack (remove selection)
                        selected(choice) = false
                    }
                }
            }
        }

        val res = ArrayBuffer[List[Int]]()
        util(temp, Queue(), res)
        res.toList
    }

    def main(args: Array[String]): Unit = {
        println(permute(Array(1, 2, 3, 4)))
    }
}