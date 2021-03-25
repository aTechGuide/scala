package in.kamranali.fpcourse.strings

import scala.annotation.tailrec

object CheckValidParentheses extends App {

  // Complexity O(N)
  def hasValidParentheses(string: String): Boolean = {

    @tailrec
    def hasValidParenTailrec(remaining: String, openParens: Int): Boolean = {
      if (remaining.isEmpty) openParens == 0
      else if (remaining.head == ')' && openParens == 0) false
      else if (remaining.head == '(') hasValidParenTailrec(remaining.tail, openParens + 1)
      else hasValidParenTailrec(remaining.tail, openParens - 1)
    }

    hasValidParenTailrec(string, 0)
  }

  assert(hasValidParentheses("()"))
  assert(hasValidParentheses("()()"))
  assert(hasValidParentheses("(())"))
  assert(!hasValidParentheses(")("))
  assert(!hasValidParentheses(")()"))
  assert(!hasValidParentheses("())s"))
}
