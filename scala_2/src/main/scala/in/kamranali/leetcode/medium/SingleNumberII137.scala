package in.kamranali.leetcode.medium

object SingleNumberII137 {
  
  // https://www.youtube.com/watch?v=cOFAmaMBVps
  // Using XOR and AND Combination
  def singleNumber(nums: Array[Int]): Int = {

    def util(idx: Int, ones: Long, twos: Long): Int = {
      
      if (idx == nums.length) ones.toInt
      else {
        val elem: Long = nums(idx)
        
        val nOnes = (ones ^ elem) & (~twos)
        val nTwos = (twos ^ elem) & (~nOnes)
        
        util(idx + 1, nOnes, nTwos)
      }
    }
    
    util(0, 0L, 0L)
  }
  
  /*
    Reference ->  https://www.geeksforgeeks.org/find-the-element-that-appears-once/
    
    Array [] : [a, a, a, b, b, b, c, c, c, d] 
    
    Mathematical Equation = ( 3*(a+b+c+d) – (a + a + a + b + b + b + c + c + c + d) ) / 2
    In more simple words: ( 3*(sum_of_array_without_duplicates) – (sum_of_array) ) / 2
    
    Time Complexity: O(Nlog(N)) 
    Auxiliary Space: O(N)
    
  */
  def singleNumber1(nums: Array[Int]): Int = {

    val uniqueElemSum: Long = nums.toSet.foldLeft(0L)(_ + _)
    val numsSum: Long = nums.foldLeft(0L)(_ + _)

    ((3*uniqueElemSum - numsSum) / 2).toInt
  }

  def main(args: Array[String]): Unit = {
    val data = Array(1,1,1,99)

    println(SingleNumberII137.singleNumber(data))
  }
}