package in.kamranali.leetcode.medium

object SubsetsII90 {
  def subsetsWithDup(nums1: Array[Int]): List[List[Int]] = {
    
    var ans: List[List[Int]] = List(List())
    val nums = nums1.sorted
    
    
    def backtracking(position: Int, temp: List[Int]): Unit = {

      for {
        choice <- position until nums.length
      } {

        val elem = nums(choice)

        if (choice > position && nums(choice) == nums(choice - 1)) {
          // Boundary of Choices begins with `position` so we can't compare it with the previous and skip
          // Hence the skipping starts with second element
          
          () // Skip this choice altogether and look for next choice
          // backtracking(choice + 1, temp) <-- This is wrong
        } else {
          val nTemp = temp :+ elem
        
          // add it to the answer
          ans = ans :+ nTemp

          backtracking(choice + 1, nTemp)
        }
        
      }
    }
    
    backtracking(0, List())
    ans
    
  }
  
  def subsetsWithDup1(nums: Array[Int]): List[List[Int]] = {

    val sortedNums = nums.sorted
    def util(idx: Int, prevSet: List[List[Int]], res: List[List[Int]]): List[List[Int]] = {

      if (sortedNums.length == idx) res
      else {
        val current = sortedNums(idx)
        val prev = sortedNums(idx - 1)

        val newRes =
          if (current != prev) res.map(_ :+ current)
          else prevSet.map(_ :+ current)

        util(idx + 1, newRes, res ++ newRes)
      }
    }

    util(1, List(List(sortedNums(0))), List(List(), List(sortedNums(0))))

  }
}