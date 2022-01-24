package in.kamranali.leetcode.easy

object Maximum69Number1323 {

  def maximum69Number (num: Int): Int = {
    num.toString.replaceFirst("6", "9").toInt
  }

  def maximum69Number1 (num: Int): Int = {

    val sNum = num.toString
    val digits = sNum.length

    def change(n: Int): Int = {
      if (n == digits) num
      else {
        val digit = sNum.charAt(n)

        if (digit == '9') change(n+1)
        else {
          val adder = 3 * math.pow(10, digits - n- 1).toInt
          num + adder
        }
      }
    }

    change(0)
  }

  def main(args: Array[String]): Unit = {
    println(maximum69Number(9996))
  }
}