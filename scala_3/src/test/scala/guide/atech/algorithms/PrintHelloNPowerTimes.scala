package guide.atech.algorithms

object PrintHelloNPowerTimes {
  val toPrint = "hello"

  def myPow(n: Int): Long = {

    // Utility Function
    def util(idx: Long): Long = {
      if (idx == 1) n
      else {
        val subProb = util(idx / 2)
        if (idx % 2 == 0) subProb * subProb // Even Scenario
        else n * subProb * subProb // Odd Scenario
      }
    }

    // Driver
    if (n == 0) 1
    else if (n == 1) 1
    else util(n)
  }

  def myPowPrint(n: Int): String = {

    // Utility Function
    def util(idx: Long): String = {
      if (idx == 1) toPrint
      else {
        val subProb = util(idx / 2)
        if (idx % 2 == 0) subProb + subProb // Even Scenario
        else toPrint + subProb + subProb // Odd Scenario
      }
    }

    // Driver
    if (n == 0) ""
    else if (n == 1) toPrint
    else util(n)
  }

  def main(args: Array[String]): Unit = {
    println(myPow(3))
    println(myPowPrint(3))
  }

}
