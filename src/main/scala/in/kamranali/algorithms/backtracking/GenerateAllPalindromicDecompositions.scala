package in.kamranali.algorithms.backtracking

/**
  *
  * Leet Code -> https://leetcode.com/problems/palindrome-partitioning/submissions/
  *
  * Reference
  * - https://www.youtube.com/watch?v=4ykBXGbonlA
  * - https://www.geeksforgeeks.org/given-a-string-print-all-possible-palindromic-partition/
  */

class GenerateAllPalindromicDecompositions {

  def partition(s: String): List[List[String]] = {
    import scala.collection.mutable.ArrayBuffer

    def isPalin(input: String, idx: Int, currIdx: Int): Boolean = {

      var start = idx
      var end = currIdx

      while (start < end) {
        if (input.charAt(start) != input.charAt(end)) return false
        start += 1
        end -= 1
      }

      true
    }

    def partitionUtil(input: String, idx: Int, decomposition: ArrayBuffer[String], result:ArrayBuffer[List[String]]): Unit = {

      if (idx == input.length) result += decomposition.toList
      else {
        (idx until input.length).foreach { currIdx =>

          // If current slice results in palindrome find remaining slices else explore next slice
          if (isPalin(input, idx, currIdx)) {

            val slice = input.slice(idx, currIdx + 1)

            // Choose
            decomposition += slice

            // Explore
            partitionUtil(input, currIdx + 1, decomposition, result)

            // Unchoose
            decomposition.remove(decomposition.length - 1)

          }
        }
      }
    }

    val res = ArrayBuffer[List[String]]()
    partitionUtil(s, 0, ArrayBuffer[String](), res)

    res.toList
  }

}

object GenerateAllPalindromicDecompositions extends App {
  val sol = new GenerateAllPalindromicDecompositions()
  var A: String = _
  var data: List[List[String]] = _

  // Test 1
  A = "aab"
  data = sol.partition(A)
  println(data)

}
