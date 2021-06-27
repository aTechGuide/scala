package in.kamranali.leetcode.medium

object NextGreaterElementII503 {
    def nextGreaterElements(nums: Array[Int]): Array[Int] = {

        /*
            - Recurse the Array twice
            - maintain decreasing stack of Indexes [Update it only in first iteration]

         */
        val length = nums.length
        val ans = Array.fill[Int](length)(-1)

        def util(idx: Int, stack: List[Int], res: Array[Int]): Array[Int] = {
            
            if (idx == 2 * length) res
            else if (stack.isEmpty) {
                if (idx < length) util(idx + 1, idx :: stack, res)
                // Second Iteration: We already have an answer
                else res
            }
            else {
                val curElement = nums(idx % length)
                val stackTop = nums(stack.head % length)
                
                if (curElement <= stackTop)
                    if (idx < length) util(idx + 1, idx :: stack, res)
                    else util(idx + 1, stack, res)
                else {
                    res(stack.head) = curElement // Found nextGreater for Stack Top Element
                    util(idx, stack.tail, res) // Further adjusting the top of stack

                }
            }
        }

        util(0, List(), ans)
    }

    def main(args: Array[String]): Unit = {
        println(nextGreaterElements(Array(1,1,1,1,1)).mkString(" "))
    }
}