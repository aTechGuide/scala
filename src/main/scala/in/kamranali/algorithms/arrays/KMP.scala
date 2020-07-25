package in.kamranali.algorithms.arrays

/**
  *
  * This algorithm takes advantage of successful comparisons that we have made between S and P
  *
  * Notes in IPad under (Algorithms -> Array)
  * LeetCode -> https://leetcode.com/problems/implement-strstr/
  *
  * Solution 1 -> Brute Force (Time = O(S * P))
  *
  * KMP ->
  * - Building Table
  *   - Time -> O(P)
  *   - Space -> O(P)
  * - Matching Traversal
  *   - Time -> O(S)
  *   - Space -> O(1)
  * - Over All
  *   - Time -> O(S + P)
  *   - Space -> O(P)
  *
  *
  *
  * Reference
  * - https://www.youtube.com/watch?v=BXCEFAzhxGY
  */

class KMP {

  def strStr(haystack: String, needle: String): Int = {

    def computingPreSuffTable(str: String): Vector[Int] = {

      var start = 0
      var end = 1
      var ans = Vector[Int](0)

      while ( end < str.length) {

         if (str(start) == str(end)) {

           start += 1
           end += 1
           ans = ans :+ start
        } else if(start > 0) {

          // start will be value of start - 1 in `ans`
          start = ans(start - 1)
        } else {
           end += 1
           ans = ans :+ 0
        }
      }
      ans
    }

    if (needle.length == 0) 0
    else {
      val lookUp = computingPreSuffTable(needle)
      var j = 0
      var i = 0

      while ( j < haystack.length) {

        while ((j < haystack.length) && (i < needle.length) && haystack(j) == needle(i)) {
          i += 1
          j += 1
        }

        if (i == needle.length) return j - i
        else if ( i > 0) {
          // Mismatch happens at i. So we will check for suffix = prefix in `i - 1`
          i = lookUp(i - 1)
        } else {
          j += 1
        }

      }

      -1
    }
  }

}

object KMP extends App {
  val sol = new KMP()
  var S: String = _
  var P: String = _
  var data: Int = _

  // Test 1
  S = "adsgwadsxdsgwadsgz"
  P = "dsgwadsgz"
  data = sol.strStr(S, P)
  assert(data == 9)

  // Test 2
  S = "aaaaab"
  P = "aaab"
  data = sol.strStr(S, P)
  assert(data == 2)

  // Test 3
  S = "hello"
  P = "ll"
  data = sol.strStr(S, P)
  assert(data == 2)

  // Test 4
  S = "aabaaabaaac"
  P = "aabaaac"
  data = sol.strStr(S, P)
  assert(data == 4)

}
