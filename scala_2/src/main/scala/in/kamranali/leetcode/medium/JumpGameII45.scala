package in.kamranali.leetcode.medium

object JumpGameII45 {

  // For linear Solution - https://www.youtube.com/watch?v=vBdo7wtwlXs
  def jump(nums: Array[Int]): Int = {

    def minJump(level: Int, ladder: Int, stairs: Int, jump: Int): Int = {

      if (level == nums.length - 1 || (level + stairs) > nums.length - 1) {
        jump
      }
      // else if (stairs == 0) minJump(level, ladder, ladder - level, jump + 1)
      else {
        val newPossibleLadder = nums(level) + level // Absolute ladder length
        val newLadder = math.max(ladder, newPossibleLadder)

        val newStairs = stairs - 1

        if (newStairs == 0) minJump(level + 1, newLadder, newLadder - level, jump + 1)
        else minJump(level + 1, newLadder, stairs - 1, jump)

        // minJump(level + 1, newLadder, stairs - 1, jump)
      }

    }

    if (nums.length <= 1) 0
    else minJump(1, nums(0), nums(0), 1)
  }
    def jump1(nums: Array[Int]): Int = {
        
        val memory = Array.fill[Int](nums.length)(0)
        
        def find(idx: Int): Int = {
            if (idx < 0) memory(0)
            else {
                val currMaxJump = nums(idx)

                val min = (1 to currMaxJump).foldLeft[Int](Int.MaxValue)((accum, b) => {
                    if (idx + b < nums.length) {
                      if (memory(idx + b) == Int.MaxValue) math.min(accum, memory(idx + b))
                      else math.min(accum, 1 + memory(idx + b))
                    }
                    else 1
                })
                
                memory(idx) = min
                
                find(idx - 1)
            }
        }
        
        val ans = find(nums.length - 2)
        println(memory.mkString(" "))
      ans
    }

  // Time = O(N ^ 2), Space O(N)
  // https://www.youtube.com/watch?v=BRnRPLNGWIo
  def main(args: Array[String]): Unit = {
    println(jump(Array(1, 2,3)))
  }
}