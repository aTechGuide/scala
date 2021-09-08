package in.kamranali.leetcode.medium

object SortColors75 {
    def sortColors(nums: Array[Int]): Unit = {

    def swap(i: Int, j: Int): Unit = {
      val temp = nums(i)
      nums(i) = nums(j)
      nums(j) = temp
    }
      
    def util(zero: Int, one: Int, two: Int): Unit = {

      if (one <= two) {
        val oneElement = nums(one)
        if (oneElement == 0) {
          swap(zero, one)
          util(zero + 1, one + 1, two)
        } else if (oneElement == 2) {
          swap(one, two)
          util(zero, one, two - 1)
        } else {
          util(zero, one + 1, two)
        }
      } else ()
    }

    if (nums.length == 1) ()
    else util(0, 0, nums.length - 1)
  }

  def main(args: Array[String]): Unit = {
    val data = Array(0, 1, 2, 0)
    sortColors(data)
    println(data.mkString(" "))
  }
}