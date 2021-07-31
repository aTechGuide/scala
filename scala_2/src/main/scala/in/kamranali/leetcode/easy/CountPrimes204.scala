package in.kamranali.leetcode.easy

object CountPrimes204 {

  def countPrimes(n: Int): Int = {

    val memory = Array.fill[Boolean](n + 1)(true)

    def markMultiplesFalse(itr: Int, factor: Int): Unit = {

      if (itr > n) ()
      else {
        memory(itr) = false
        markMultiplesFalse(itr + factor, factor)
      }
    }

    def compute(number: Int): Unit = {

      if (number > n) ()
      else {
        if (memory(number)) {
          markMultiplesFalse(number + number, number)
          compute(number + 1)
        }
        else compute(number + 1)
      }
    }

    if (n == 0) 0
    else if (n == 1) 0
    else if (n == 2) 0
    else {
      compute(2)
      val count = memory.count(_ == true) - 2
      if (memory(n)) count - 1
      else count
    }

  }

  def main(args: Array[String]): Unit = {
    println(countPrimes(10))
  }

}
