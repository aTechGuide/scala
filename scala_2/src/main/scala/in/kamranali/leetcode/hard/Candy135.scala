package in.kamranali.leetcode.hard

object Candy135 {
    def candy(ratings: Array[Int]): Int = {
      
      /*
        Conditions
          - [left]  if rating(i - 1) < rating(i) => if (i-1) = x and i = (x + 1)
          - [right] if rating(i + 1) < rating(i) => if (i+1) = x and i = (x + 1)
      */
      
      val len = ratings.length
      
      // condition 1
      val left = Array.fill[Int](len)(1)
      
      (1 until len).foreach {idx => {
        if (ratings(idx) > ratings(idx - 1))
          left(idx) = left(idx - 1) + 1
      }}
      
      // condition 2
      val right = Array.fill[Int](len)(1)
      
      ((len - 2) to 0 by -1).foreach {idx => {
        if (ratings(idx) > ratings(idx + 1))
          right(idx) = right(idx + 1) + 1
      }}
      
      // Step 3: Find max at each index
      def minCandy(idx: Int, ans: Int): Int = {
        
        if (idx == len) ans
        else {
          val maxVal = math.max(left(idx), right(idx))
          minCandy(idx + 1, ans + maxVal)
        }
      }

      println(left.mkString(" "))
      println(right.mkString(" "))
      minCandy(0, 0)
    }

  def main(args: Array[String]): Unit = {
    val data = Array(1,2,87,87,87,2,1)

    println(candy(data))
  }
}