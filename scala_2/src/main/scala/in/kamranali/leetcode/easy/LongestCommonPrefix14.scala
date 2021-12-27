package in.kamranali.leetcode.easy

object LongestCommonPrefix14 {
  /*
  Ref https://leetcode.com/problems/longest-common-prefix/solution/
    Approach 1: Horizontal scanning
  */
  def longestCommonPrefix(strs: Array[String]): String = {
        
    def calculateMatching(st1: String, st2: String, idx: Int, matched: String): String = {
      if (idx == st1.length) st1
      else if (idx == st2.length) st2
      else if (st1.charAt(idx) == st2.charAt(idx)) calculateMatching(st1, st2, idx + 1, matched + st1.charAt(idx).toString)
      else matched
    }
        
    def traverse(idx: Int, prev: String): String = {

      if (idx == strs.length) prev
      else {
        val curr = strs(idx)
        val ans = calculateMatching(prev, curr, 0, "")

        if (ans.isEmpty) ans
        else {
            traverse(idx + 1, ans)
        }
      }

    }

    traverse(1, strs(0)) 
  }
}