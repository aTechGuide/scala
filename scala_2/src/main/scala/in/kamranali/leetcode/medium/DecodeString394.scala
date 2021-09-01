package in.kamranali.leetcode.medium

object DecodeString394 {
    def decodeString(s: String): String = {
      val len = s.length

      def parse(idx: Int, cur: String, k: Int, intStack: List[Int], strStack: List[String]): String = {
        if (idx == len) cur
        else {
          val c = s.charAt(idx)

          if (c.isDigit) {
            // build multiplier
            val newK = k * 10 + (c - '0')
            parse(idx + 1, cur, newK, intStack, strStack)
          } else if (c == '[') {
            parse(idx + 1, "", 0, k :: intStack, cur :: strStack)
          } else if (c == ']') {
            val newCurr = strStack.head + (cur * intStack.head)
            parse(idx + 1, newCurr, k, intStack.tail, strStack.tail)
          } else {
            // its a character
            parse(idx + 1, cur + c.toString, k, intStack, strStack)
          }
        }
      }

      parse(0, "", 0, List(), List())
    }

  def main(args: Array[String]): Unit = {
//    println("abc" * 3)

//    println(decodeString("3[a]2[bc]")) // aaabcbc

    println(decodeString("3[a2[c]]")) // accaccacc

    //println(decodeString("100[leetcode]"))

    println(decodeString("2[2[y]]"))
  }
}