package in.kamranali.leetcode.medium

import scala.collection.mutable

/*
    For logic
    - https://www.youtube.com/watch?v=th4OnoGasMU

    For Code
    - https://github.com/mission-peace/interview/blob/master/src/com/interview/dynamic/BreakMultipleWordsWithNoSpaceIntoSpace.java
 */
object WordBreak139 {

    // Optimized Version
    def wordBreak3(s: String, wordDict: List[String]): Boolean = {

        val wordSet = wordDict.toSet
        val len = s.length
        val memory = Array.fill[Boolean](len + 1)(false)

        memory(0) = true // Marking empty string as True

        for {
            right <- 1 to len
            left <- 0 until right
        } {
            if (memory(left) && wordSet.contains(s.substring(left, right)))
                memory(right) = true
        }

        println(memory.mkString(" "))
        memory(len)
    }

    def wordBreak1(s: String, wordDict: List[String]): Boolean = {
        
        val len = s.length
        val dict = wordDict.toSet
        
        def wb(pos: Int, i: Int): Boolean = {
            if (pos == len) true
            else if (i > len) false
            else {
                if (dict.contains(s.substring(pos, i))) {
                    if (wb(i, i)) true /* Search in substring for a solution */
                    else wb(pos, i + 1) /* Ignore this choice and look for other choices */
                }
                else wb(pos, i + 1) /* Didn't find in dictionary check others */
            }
        }
        
        wb(0, 0)
    }

    /*
        O(N^2 * N) where N = substring complexity
     */
    def wordBreak2(s: String, wordDict: List[String]): Boolean = {

        val len = s.length
        val dict = wordDict.toSet
        // we can't take Array as we can't assume beforehand that values of indexes is false
        val memory: mutable.Map[Int, Boolean] = mutable.Map.empty[Int, Boolean]

        def wb(pos: Int, i: Int): Boolean = {
            if (pos == len) true
            else if (i > len) false
            else if (memory.contains(pos)) {
                // println("Found in memory")
                memory(pos)
            }
            else {
                val ans = if (dict.contains(s.substring(pos, i))) { /* If word found in dictionary */
                    if (wb(i, i)) true /* Search in substring for a solution */
                    else wb(pos, i + 1) /* We can't make a solution if choose above word of dictionary try other words */
                } else wb(pos, i + 1)

                memory.put(pos, ans) // memorise the solution for future use
                memory(pos)
            }
        }

        val ans = wb(0, 0)
        println(memory)
        ans
    }

    def main(args: Array[String]): Unit = {
//        val str = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab"
//        println(wordBreak2(str, List("a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa")))

        val str = "leetcode"
        println(wordBreak3(str, List("leet", "code")))
    }
}