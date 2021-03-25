package in.kamranali.fpcourse.strings

import scala.collection.immutable.TreeMap

/**
  * rearrange chars so that no two adjacent chars are identical
  * - "aaabc" -> "abaca", "acaba"
  * - "aaa" -> ""
  *
  * Leetcode -> https://leetcode.com/problems/reorganize-string/
  */
object ReorganiseString {



  /*
      If one of the chars occur more than half of the length of the string. Then we can't have a String

      {a-3, b-1, c-1}
      ot({a-3, b-1, c-1}, \0 "") =
      ot({a-2, b-1, c-1}, 'a', "a") =
      ot({a-2, c-1}, 'b', "ab") =
      ot({a-1, c-1}, 'a', "aba") =
      ot({a-1}, 'c', "abac") =
      ot({}, 'a', "abaca") =
      "abaca"

      Complexity: O(N^2) time
      Better complexity: use TreeMap: O(N*log(N))

     */
  def reorganizeString(string: String): String = {

    def organiseTailRec(cCount: Map[Char, Int], forbidded: Char = '\u0000', acc: String = ""): String = {
      if (cCount.isEmpty) acc
      else {
        val newChar = cCount.filter(_._1 != forbidded).maxBy(_._2)._1 // HashMap O(N) for each Char, TreeMap O(1)
        val newCCount =
          if (cCount(newChar) == 1) cCount - newChar
          else cCount + (newChar -> (cCount(newChar) - 1)) // Tree Map O(LogN)

        organiseTailRec(newCCount, newChar, acc + newChar)
      }
    }

    val charCount = string.foldLeft(Map.empty[Char, Int]) {
      case (map, newChar) => map + (newChar -> (map.getOrElse(newChar, 0) + 1))
    }

    if (charCount.values.exists( _ > (string.length + 1)/ 2)) ""
    else organiseTailRec(charCount)

  }

}
