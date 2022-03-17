package in.kamranali.leetcode.hard

object WordLadder127 {

  // https://leetcode.com/problems/word-ladder/discuss/40707/C%2B%2B-BFS
  def ladderLength(beginWord: String, endWord: String, wordList: List[String]): Int = {
    import scala.collection.mutable

    val wordLen = beginWord.length

    // Auxiliary Functions (Word, level)
    def length(q: mutable.Queue[(String, Int)], remainingWords: Set[String]): Int = {
      if (q.isEmpty) 0 // No path found
      else {
        val (word, level) = q.dequeue()

        if (word == endWord) level
        else {
          val wordsSet = remainingWords - word

          // generate probable words
          (0 until wordLen).foreach { wordIdx => {
            val chArray = word.toCharArray

            ('a' to 'z').foreach { c => {

              chArray(wordIdx) = c

              val probWord = chArray.mkString

              if (wordsSet.contains(probWord)) {
                q.enqueue((probWord, level + 1))
              }
            }}
          }}

          length(q, wordsSet)
        }
      }
    }

    // Driver
    length(mutable.Queue[(String, Int)]((beginWord, 1)), wordList.toSet)
  }

  // TLE
  def ladderLength1(beginWord: String, endWord: String, wordList: List[String]): Int = {
    import scala.collection.mutable.Map
    
    val len = wordList.length
    val comparisonMemory = Map.empty[(String, String), Boolean]
    val chosenWord = Map.empty[String, Boolean]
    
    wordList.foreach { word => {
      chosenWord.put(word, false)
    }}
    
    // Utility Methods
    def comparison(s1: String, s2: String, diff: Int = 0, idx: Int = 0): Boolean = {
      if (diff > 1) false
      else if (s1.length == idx) true
      else if (s1.charAt(idx) == s2.charAt(idx)) comparison(s1, s2, diff, idx + 1)
      else comparison(s1, s2, diff + 1, idx + 1)
    }
    
    def comparisonCache(s1: String, s2: String): Boolean = {
      if (comparisonMemory.contains(s1 -> s2)) comparisonMemory(s1 -> s2)
      else {
        val ans = comparison(s1, s2)
        comparisonMemory.put(s1 -> s2, ans)
        ans
      }
    }
    
    def f(curr: String): Int = {
      val answer = comparisonCache(beginWord, curr)
      if (answer) 1
      else {
        
        wordList.foldLeft(6000)((accum, word) => {
          val minW = if(curr > word) word else curr
          val maxW = if(curr > word) curr else word
          
          val isValidPath = comparisonCache(minW, maxW)
          
          if (isValidPath && !chosenWord(word)) {
            chosenWord.put(word, true)
            val res = math.min(accum, f(word) + 1)
            
            chosenWord.put(word, false)
            res
          } else {
            accum
          }
        })
      }
    }
    
    if (!wordList.contains(endWord)) 0
    else f(endWord) + 1
    
  }

  def main(args: Array[String]): Unit = {
    println(ladderLength("hit", "dot", List("dot", "hot")))
  }
}