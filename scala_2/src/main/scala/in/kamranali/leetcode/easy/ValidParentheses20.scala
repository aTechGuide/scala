package in.kamranali.leetcode.easy

object ValidParentheses20 {
    def isValid(s: String): Boolean = {

    def isBal(idx: Int, memory: List[Char]): Boolean = {

      if (idx == s.length) {
        if (memory.isEmpty) true
        else false
      } else {
        val curr: Char = s(idx)

        if (curr == '{') {
          isBal(idx + 1, '}' :: memory)
        } else if (curr == '[') {
          isBal(idx + 1, ']' :: memory)
        } else if (curr == '(') {
          isBal(idx + 1, ')' :: memory)
        } else {
          if (memory.isEmpty) false
          else {

            if (curr == memory.head) isBal(idx + 1, memory.tail)
            else false
          }
        }
      }
    }

    if (s.length % 2 != 0) false
    else isBal(0, List[Char]())
  }
}