package in.kamranali.algorithms.dp

/*
  *
  * Time
  * - Brute Force -> O(2^n)
  * - DP - O(n)
  *
  * Space - O(n)
  *
  * Ref
  * - https://www.youtube.com/watch?v=YcJTyrG3bZs
  * - https://leetcode.com/problems/decode-ways/
  * - https://www.geeksforgeeks.org/count-possible-decodings-given-digit-sequence/
  */

class P_5_DecodeString {

  // DP Solution
  def numDecodings(s: String): Int = {

    val len = s.length

    if (len > 0 && s(0) == '0') return 0
    if (len == 0 || len == 1) return 1

    var count = Array.fill[Int](len + 1)(0)

    // base Condition
    count(0) = 1
    count(1) = 1

    (2 to len).foreach { idx =>

      if (s(idx - 1) > '0') count(idx) = count(idx - 1)

      if (s(idx - 2) == '1' || (s(idx - 2) == '2' && s(idx - 1) < '7')) count(idx) += count(idx - 2)
    }

    count(len)
  }

  // Exponential Complexity
  def numDecodingsRec(s: String): Int = {

    val len = s.length

    if (len > 0 && s(0) == '0') return 0
    if (len == 0 || len == 1) return 1

    var count = 0

    if (s(len - 1) > '0') count = numDecodingsRec(s.init)

    if (s(len - 2) == '1' || (s(len - 2) == '2' && s(len - 1) < '7'))
      count += numDecodingsRec(s.init.init)

    count
  }

}

object P_5_DecodeString extends App {
  val sol = new P_5_DecodeString()
  var A: String = _
  var data: Int = _

  // Test 1
  A = "226"
  data = sol.numDecodings(A)
  assert(data == 3)

  // Test 2
  A = "1234"
  data = sol.numDecodings(A)
  assert(data == 3)

}
