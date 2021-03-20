package in.kamranali.algorithms.dp

/**
  *
  * Ref
  * - https://www.youtube.com/watch?v=NnD96abizww
  * - https://leetcode.com/problems/longest-common-subsequence/
  */

class P_6_Longest_Common_Subsequence {

  def longestCommonSubsequence(text1: String, text2: String): Int = {

    val rows = text1.length
    val cols = text2.length
    val memory = Array.ofDim[Int](rows + 1, cols + 1)

    for (row <- 1 to rows; col <- 1 to cols) {
      memory(row)(col) =
      if (text1(row-1) == text2(col-1)) memory(row - 1)(col - 1) + 1
      else math.max(memory(row - 1)(col), memory(row)(col - 1))

    }

//    memory.foreach(row => println(row.mkString(" ")))

    memory(rows)(cols)
  }
}

object P_6_Longest_Common_Subsequence extends App {

  val sol = new P_6_Longest_Common_Subsequence()
  var A: String = _
  var B: String = _
  var data: Int = _

  // Test 1
  A = "abcde"
  B = "ace"
  data = sol.longestCommonSubsequence(A, B)
  assert(data == 3)

  // Test 2
  //  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
  //  data = sol.findLength(A, B)
  //  assert(data == 42)

}


