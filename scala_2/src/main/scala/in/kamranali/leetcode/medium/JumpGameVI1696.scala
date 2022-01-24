package in.kamranali.leetcode.medium

object JumpGameVI1696 {
  def maxResult(nums: Array[Int], k: Int): Int = {
    import scala.collection.mutable

    val len = nums.length
    val dq = mutable.ArrayDeque[Int](0) // Keep indexes in DQ

    def bottomUp(idx: Int): Unit = {
      if (idx == len) ()
      else {

        // Remove the invalid index
        if (dq.last + k < idx) dq.removeLast()

        nums(idx) = nums(idx) + nums(dq.last)

        while (dq.nonEmpty && nums(dq.head) <= nums(idx)) {
          dq.removeHead()
        }

        dq.prepend(idx)
        bottomUp(idx + 1)
      }
    }

    bottomUp(1)
    nums.last
  }

  // With DP [Bottom Up, Tabulation, K memory], Gives Time Limit Exceeded
  def maxResult4(nums: Array[Int], k: Int): Int = {
    import scala.collection.mutable.ListBuffer
    
    val len = nums.length
    val memory = ListBuffer[Int]()
    memory.append(nums(0))
    
    def bottomUp(idx: Int): Unit = {
      if (idx == len) ()
      else {

        if (memory.size > k) memory.remove(0)

        val max = memory.max
        val ans = max + nums(idx)
        
        memory.append(ans)
        bottomUp(idx + 1)
      }
    }
    
    // Fill memory
    bottomUp(1)
    
    // Return answer
    memory.last
  }
  
  // With DP [Bottom Up, Tabulation], Gives Time Limit Exceeded
  def maxResult3(nums: Array[Int], k: Int): Int = {
    
    val len = nums.length
    val memory = Array.fill[Int](len)(Int.MinValue)
    memory(0) = nums(0)
    
    def bottomUp(idx: Int): Unit = {
      if (idx == len) ()
      else {
        val left = math.max(0, idx - k)
        
        val ans = (left to idx - 1).foldLeft(Int.MinValue)((acc, step) => {
          math.max(acc, memory(step) + nums(idx))
        })
        
        memory(idx) = ans
        bottomUp(idx + 1)
      }
    }
    
    // Fill memory
    bottomUp(1)
    
    // Return answer
    memory(len-1)    
  }
  
  // With DP [Top Down, Memoization], Gives Memory Limit Exceeded
  def maxResult2(nums: Array[Int], k: Int): Int = {
    
    val memory = Array.fill[Int](nums.length)(Int.MinValue) // Step 1: Define cache
    
    def topDown(idx: Int): Int = {
      if (idx == 0) nums(0)
      else if (memory(idx) != Int.MinValue) memory(idx) // Step 3: Check if we have an answer
      else {
        val left = math.max(0, idx - k)
        
        val ans = (left to idx - 1).foldLeft(Int.MinValue)((acc, step) => {
          math.max(acc, topDown(step) + nums(idx))
        })
        
        // Step 2: Save answer before returning
        memory(idx) = ans
        ans
      }
    }
    
    topDown(nums.length -1)
        
  }
  
  // Without DP
  def maxResult1(nums: Array[Int], k: Int): Int = {
    
    def topDown(idx: Int): Int = {
      if (idx == 0) nums(0)
      else {
        val left = math.max(0, idx - k)
        
        val ans = (left to idx - 1).foldLeft(Int.MinValue)((acc, step) => {
          math.max(acc, topDown(step) + nums(idx))
        })
        
        ans
      }
    }
    
    topDown(nums.length -1)
        
  }
}