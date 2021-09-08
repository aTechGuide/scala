package in.kamranali.leetcode.easy

object MoveZeroes283 {
  
  def moveZeroes(nums: Array[Int]): Unit = {
    def swap(i: Int, j: Int): Unit = {
      val temp = nums(i)
      nums(i) = nums(j)
      nums(j) = temp 
    }
    
    def fix(zero: Int, nonZero: Int): Unit = {
      
      if (nonZero == nums.length) ()
      else {
        val elem = nums(nonZero)
        
        if (elem != 0) {
          if (nums(zero) != 0) {
            // do nothing
            fix(zero + 1, nonZero + 1)
          } else {
            swap(zero, nonZero)
            fix(zero + 1, nonZero + 1)
          }
        } else {
          fix(zero, nonZero + 1)
        }
      }
      
    }
    
    fix(0, 0)
  }
  
  def moveZeroes1(nums: Array[Int]): Unit = {

      def fix(idx: Int, prev: Int, zeroC: Int): Int = {

          if (idx == nums.length) zeroC
          else {
              val curr = nums(idx)

              if (curr == 0) fix(idx + 1, prev, zeroC + 1)
              else {
                  nums(prev) = curr
                  fix(idx + 1, prev + 1, zeroC)
              }
          }
      }

      def addZero(idx: Int, count: Int): Unit = {

          if (count == 0) ()
          else {
              nums(idx) = 0
              addZero(idx - 1, count - 1)
          }
      }

      val zeroCount = fix(0, 0, 0)

      addZero(nums.length - 1, zeroCount)


  }

  def main(args: Array[String]): Unit = {
    val data = Array(0,1,0,3,12)
    moveZeroes(data)
    println(data.mkString(" "))
  }
}