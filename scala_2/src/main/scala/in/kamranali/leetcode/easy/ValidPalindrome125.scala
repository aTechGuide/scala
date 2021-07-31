package in.kamranali.leetcode.easy

object ValidPalindrome125 {

  def isPalindrome(s: String): Boolean = {

    def check(left: Int, right: Int): Boolean = {

      if (left > right) true
      else if (!s.charAt(left).isLetterOrDigit) check(left + 1, right)
      else if (!s.charAt(right).isLetterOrDigit) check(left, right - 1)
      else {

        val leftV = s.charAt(left).toLower
        val rightV = s.charAt(right).toLower

        if (leftV == rightV) check(left + 1, right -1)
        else false
      }
    }

    check(0, s.length - 1)

  }

  def main(args: Array[String]): Unit = {

    val regex = "[a-zA-Z0-9]".r

    val ans = regex.findAllIn("A man, a plan, a canal: Panama")
    println(ans.mkString(""))
  }

}
