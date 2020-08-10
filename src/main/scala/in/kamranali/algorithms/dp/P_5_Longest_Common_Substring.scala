package in.kamranali.algorithms.dp

/**
  *
  * Leet code variant is solved here
  *
  * Ref
  * - https://www.youtube.com/watch?v=BysNXJHzCEs
  * - https://leetcode.com/problems/maximum-length-of-repeated-subarray/
  */

class P_5_Longest_Common_Substring {

  def findLength(A: Array[Int], B: Array[Int]): Int = {

    val rows = A.length
    val cols = B.length
    val memory = Array.ofDim[Int](rows + 1, cols + 1)

    var max = 0

    for (row <- 1 to rows; col <- 1 to cols) {
      memory(row)(col) =
      if (A(row-1) == B(col-1)) memory(row - 1)(col - 1) + 1
      else 0

      max = if (memory(row)(col) > max) memory(row)(col) else max
    }


//    memory.foreach(row => println(row.mkString(" ")))

    max

  }

}

object P_5_Longest_Common_Substring extends App {
  val sol = new P_5_Longest_Common_Substring()
  var A: Array[Int] = _
  var B: Array[Int] = _
  var data: Int = _

  // Test 1
  A = Array[Int](1,2,3,2,1)
  B = Array[Int](3,2,1,4,7)
  data = sol.findLength(A, B)
  assert(data == 3)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.findLength(A, B)
//  assert(data == 42)

}
