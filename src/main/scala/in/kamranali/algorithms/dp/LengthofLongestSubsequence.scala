package in.kamranali.algorithms.dp

/**
  * https://www.interviewbit.com/problems/length-of-longest-subsequence/
  */

class LengthofLongestSubsequence {

  def longestSubsequenceLength(A: Array[Int]): Int  = {

    if (A.isEmpty) return 0

    val increasing = Array.fill[Int](A.length)(1)
    val decreasing = Array.fill[Int](A.length)(1)

    A.indices.foreach{idx =>
      var j = 0

      while (j < idx) {

        if (A(idx) > A(j) && (increasing(idx) < increasing(j) + 1)) {
          increasing(idx) = increasing(j) + 1 // Fill the table
        }

        j += 1
      }
    }

    A.indices.reverse.foreach{idx =>
      var j = A.length - 1

      while (j > idx) {

        if (A(idx) > A(j) && (decreasing(idx) < decreasing(j) + 1)) {
          decreasing(idx) = decreasing(j) + 1 // Fill the table
        }

        j -= 1
      }
    }

//    println(increasing.mkString(" "))
//    println(decreasing.mkString(" "))

    val ans = increasing.zip(decreasing).map{case (i, j) => i + j -1}.max

//    println(ans)
    ans
  }

  def longestSubsequenceLengthED(A: Array[Int]): Int  = {
    val len = A.length
    if (len == 0) 0
    else {
      val lis = Array.fill[Int](len)(1)
      for {
        i <- 1 until len
        j <- 0 until i
        if A(i) > A(j) && lis(i) < lis(j) + 1
      } lis(i) = lis(j) + 1

      val lds = Array.fill[Int](A.length)(1)
      for {
        i <- len - 2 to 0 by -1
        j <- len - 1 until i by -1
        if A(i) > A(j) && lds(i) < lds(j) + 1
      } lds(i) = lds(j) + 1

      println(lis.mkString(" "))
      println(lds.mkString(" "))

      var max = lis(0) + lds(0) - 1
      for {
        i <- 1 until len
        if lis(i) + lds(i) - 1 > max
      } max = lis(i) + lds(i) - 1

      println(max)
      max
    }
  }

}

//1 2 3 4 3 5 3 2 2 1
//1 3 5 5 4 5 4 3 2 1
//9
//1 2 2 3 3 4 2 1
//1 5 2 4 3 3 2 1
//6
//1 2 3 4 5
//1 1 1 1 1
//5
//1 1 2 3 1 2 4 5 6 5 6 5
//3 2 2 2 1 1 1 3 3 2 2 1
//8

object LengthofLongestSubsequence extends App {
  val sol = new LengthofLongestSubsequence()

  // Test 1
//  1 2 3 4 3 4 3 2 2 1
//  1 3 5 5 4 5 4 3 2 1
  var A = Array[Int](1, 3, 5, 6, 4, 8, 4, 3, 2, 1)
  var data = sol.longestSubsequenceLength(A)
  assert(data == 9)

  // Test 2
//  1 2 2 3 3 4 2 1
//  1 3 2 4 3 3 2 1
  A = Array[Int](1, 11, 2, 10, 4, 5, 2, 1)
  data = sol.longestSubsequenceLength(A)
  assert(data == 6)

  A = Array[Int]( 1, 2, 3, 4, 5)
  data = sol.longestSubsequenceLength(A)
  assert(data == 5)

  A = Array[Int]( 7, 6, 8, 10, 2, 5, 12, 30, 31, 20, 22, 18)
  data = sol.longestSubsequenceLength(A)
  assert(data == 8)

}
