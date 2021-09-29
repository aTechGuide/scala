package in.kamranali.leetcode.medium

// https://www.youtube.com/watch?v=0tantogp8fc
object RemoveDuplicateLetters316 {
    def removeDuplicateLetters(s: String): String = {
      
      // Step 1: Create Frequency Map
      val freqMap = s.foldLeft[Map[Char, Int]](Map())((accum, c) => {
        val count = accum.getOrElse(c, 0) + 1
        
        accum + (c -> count)
      })
      
      val isCharAdded = Array.fill[Boolean](26)(false)
      
      def remove(idx: Int, st: List[Char], map: Map[Char, Int]): String = {
        if (idx == s.length) {
          st.reverse.mkString("")
        } else if (isCharAdded(s.charAt(idx) - 'a')) {
          // Char already added, so skip it

          // decrease frequency
          val c = s.charAt(idx)
          val count = map(c) - 1
          val newMap = map + (c -> count)

          // Skipping the character
          remove(idx + 1, st, newMap)
        } else {
          val c = s.charAt(idx)
          
          if (st.isEmpty) {
            // Stack is empty

            // decrease the count
            val count = map(c) - 1
            val newMap = map + (c -> count)

            // mark as used
            isCharAdded(c - 'a') = true
            remove(idx + 1, c :: st, newMap)
          } else {
            
            val isTopSmaller = st.head < c
            val headFreq = map(st.head)
            
            if (isTopSmaller || (headFreq == 0)) {
              // Either head is smaller Or the count = 0 i.e. we don't have head character anymore

              // decrease the count
              val count = map(c) - 1
              val newMap = map + (c -> count)

              // mark as used
              isCharAdded(c - 'a') = true
              remove(idx + 1, c :: st, newMap)
            } else {
              // remove the head character and mark head as unused

              isCharAdded(st.head - 'a') = false
              remove(idx, st.tail, map)
            }
          }
        }
      }
      
      remove(0, List(), freqMap)
    }

  def main(args: Array[String]): Unit = {
    val data = "cbacdcbc" // "acdb"
    println(removeDuplicateLetters(data))
  }
}