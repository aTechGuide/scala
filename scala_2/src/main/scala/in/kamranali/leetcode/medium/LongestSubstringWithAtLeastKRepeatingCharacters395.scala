package in.kamranali.leetcode.medium

// https://www.geeksforgeeks.org/longest-substring-where-all-the-characters-appear-at-least-k-times-set-3/
object LongestSubstringWithAtLeastKRepeatingCharacters395 {

  def longestSubstring(s: String, k: Int): Int = {

    val maxUnique = s.toSet.size

    def ls(left: Int, right: Int, currentUnique: Int, unique: Int, countAtLeastK: Int, memory: Map[Char, Int], res: Int): Int = {

      if (currentUnique > maxUnique) res
      else if (right == s.length) ls(0, 0, currentUnique + 1, 0, 0, Map(), res)
      else {

        if (unique <= currentUnique) {
          val elem = s.charAt(right)
          val currCount = memory.getOrElse(elem, 0)
          val newCount = currCount + 1
          val newMemory = memory + (elem -> newCount)

          val newUnique = if (currCount == 0) unique + 1 else unique
          val newCountAtLeastK = if (newCount == k) countAtLeastK + 1 else countAtLeastK

          val newRes = if (newUnique == currentUnique && newCountAtLeastK == newUnique) math.max(right - left + 1, res) else res

          ls(left, right + 1, currentUnique, newUnique, newCountAtLeastK, newMemory, newRes)

        } else {
          val elem = s.charAt(left)
          val currCount = memory(elem) // elem must be present in the window
          val newCount = currCount - 1
          val newMemory = memory + (elem -> newCount)

          val newUnique = if (newCount == 0) unique - 1 else unique
          val newCountAtLeastK = if (currCount == k) countAtLeastK - 1 else countAtLeastK

          val newRes = if (newUnique == currentUnique && newCountAtLeastK == newUnique) math.max(right - left, res) else res

          ls(left + 1, right, currentUnique, newUnique, newCountAtLeastK, newMemory, newRes)
        }
      }

    }

    ls(0, 0, 1, 0, 0, Map(), 0)
  }

  def main(args: Array[String]): Unit = {
    println(longestSubstring("ababacb", 3))
  }

}
