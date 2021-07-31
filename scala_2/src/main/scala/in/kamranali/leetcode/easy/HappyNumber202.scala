package in.kamranali.leetcode.easy

object HappyNumber202 {
  def isHappy(n: Int): Boolean = {

    def digitSum(num: Int, sum: Int = 0): Int = {

      if (num == 0) sum
      else {
        val digit = num % 10
        val remaining = num / 10

        digitSum(remaining, sum + math.pow(digit, 2).toInt)
      }
    }

    def check(num: Int, memory: Set[Int]): Boolean = {

      if (num == 1) true
      else if (memory.contains(num)) false
      else {
        val next = digitSum(num)
        check(next, memory + num)

      }
    }

    check(n, Set())

  }

  def main(args: Array[String]): Unit = {
    println(isHappy(19))
  }
}