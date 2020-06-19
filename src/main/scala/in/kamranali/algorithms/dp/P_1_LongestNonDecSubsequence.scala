package in.kamranali.algorithms.dp

/**
  * Explanation
  * - https://www.youtube.com/watch?v=fV-TF4OvZpk
  *
  *
  * Time O(n ^ 2)
  * Space O(n)
  */

class P_1_LongestNonDecSubsequence {

  def solve(A: Array[Int]): Int  = {

    val memory = Array.fill[Int](A.length)(1)

    A.indices.foreach{idx =>

      var j = 0
      while (j < idx) {

        if (A(idx) >= A(j)) { // <- Change this condition for longest Increasing
          memory(idx) = memory(j) + 1
        }

        j +=1
      }
    }

    // println(memory.mkString(" "))
    memory.max
  }

}

object P_1_LongestNonDecSubsequence extends App {
  val sol = new P_1_LongestNonDecSubsequence()

  // Test 1
  var A = Array[Int](-1, 3, 4, 5, 2, 2, 2, 2)
  var data = sol.solve(A)
  assert(data == 5)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.solve(A)
//  assert(data == 42)

}
