package in.kamranali.algorithms.dp

/**
  * Notes in IPad
  *
  * Ref
  * - Ben https://www.youtube.com/watch?v=MiqoA-yF-0M
  * - GS https://www.youtube.com/watch?v=XJ6e4BQYJ24
  * - Tushar https://www.youtube.com/watch?v=We3YDTzNXEk
  * - Leet https://leetcode.com/problems/edit-distance/
  */

class P_4_MinEditDistance {

  def minDistance(word1: String, word2: String): Int = {


    val cols = word1.length
    val rows = word2.length

    val memory = Array.ofDim[Int](rows + 1, cols + 1)

    for (
      row <- 0 to rows;
      col <- 0 to cols
    ) {

      memory(row)(col) =
        if (row == 0 && col == 0) 0
        else if (row == 0) memory(row)(col - 1) + 1
        else if (col == 0) memory(row - 1)(col) + 1
        else if (word1(col-1) == word2(row-1)) memory(row - 1)(col - 1)
        else (memory(row-1)(col) min memory(row)(col-1) min memory(row-1)(col-1)) + 1
    }

//    memory.foreach {row => println(row.mkString(" "))}
    memory(rows)(cols)
  }

}

object P_4_MinEditDistance extends App {
  val sol = new P_4_MinEditDistance()
  var A: String = _
  var B: String = _
  var data: Int = _

  // Test 1
  A = "abcdef"
  B = "azced"
  data = sol.minDistance(A, B)
  assert(data == 3)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.solve(A)
//  assert(data == 42)

}