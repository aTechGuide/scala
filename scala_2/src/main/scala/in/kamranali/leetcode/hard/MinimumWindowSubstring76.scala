package in.kamranali.leetcode.hard

object MinimumWindowSubstring76 {
  def minWindow(s: String, t: String): String = {
      
    val ptrLen = t.length
    val sLen = s.length

    val ptrMap = t.foldLeft[Map[Char, Int]](Map())((accum, c) => {
      val count = accum.getOrElse(c, 0) + 1
      accum + (c -> count)
    })

    // Auxilary Function
    def util(start: Int, end: Int, count: Int, strMap: Map[Char, Int], resStart: Int, resLen: Int): String = {
      if (end == sLen) {
        // After adding the last character there are chances to reduce the window

        if (resLen == Int.MaxValue) ""
        else if (!ptrMap.contains(s.charAt(start)) || strMap(s.charAt(start)) > ptrMap(s.charAt(start))) {
          // Valid Shrink State
          val startC = s.charAt(start)
          val newStart = start + 1
          val windowLen = end - newStart
          val nResLen = math.min(resLen, windowLen)
          val nStrMap = strMap + (startC -> (strMap(startC) - 1))
          val newResStart = if (nResLen == resLen) resStart else newStart

          util(newStart, end, count, nStrMap, newResStart, nResLen)
        }
        else s.substring(resStart, resStart + resLen)
      }
      else if (count < ptrLen) {
        val c = s.charAt(end)
        val nStrMap = strMap + (c -> (strMap.getOrElse(c, 0) + 1))
        val newCount = if (ptrMap.contains(c) && nStrMap(c) <= ptrMap(c)) count + 1 else count

        // first opportunity to calculate the window
        val nResLen = if (newCount == ptrLen) end - start + 1 else resLen

        if (newCount == ptrLen) util(start, end, newCount, strMap, start, nResLen) // Don't expand the window because there is an opportunity to shrink at this point
        else util(start, end + 1, newCount, nStrMap, resStart, nResLen)
      } else {
        // here count == ptrLen
        // Try Shrinking
        val startC = s.charAt(start)
        val c = s.charAt(end)
        val nStrMap = strMap + (c -> (strMap.getOrElse(c, 0) + 1))

        if (!ptrMap.contains(startC) || nStrMap(startC) > ptrMap(startC)) {
          // Adjust Window
          val newStart = start + 1
          val windowLen = end - newStart + 1
          val nResLen = math.min(resLen, windowLen)
          val newResStart = if (nResLen == resLen) resStart else newStart

          val finalStrMap = strMap + (startC -> (strMap(startC) - 1))

          util(newStart, end, count, finalStrMap, newResStart, nResLen)

        } else {
          util(start, end + 1, count, nStrMap, resStart, resLen)
        }
      }
    }

    // Code
    util(0, 0, 0, Map(), -1, Int.MaxValue)
  }

  def main(args: Array[String]): Unit = {
    println(minWindow("caweewcaefgc", "cae"))
  }
}