package in.kamranali.leetcode.medium

object DailyTemperatures739 {

  def dailyTemperaturesBF(temperatures: Array[Int]): Array[Int] = {

    temperatures
      .zipWithIndex
      .map(tuple => {
        val idx = temperatures.indexWhere(_ > tuple._1, tuple._2 + 1)
        if (idx == -1) 0 else idx - tuple._2
      })
  }

  def dailyTemperatures(temperatures: Array[Int]): Array[Int] = {

    def adjustStack(currElem: Int, currIdx: Int, stack: List[(Int, Int)]): (Int, List[(Int, Int)]) = {

      if (stack.isEmpty) {
        val newStack = List((currElem, currIdx))
        (currIdx, newStack)
      } else {
        val stackTopElement = stack.head._1
        val stackTopIdx = stack.head._2

        if (currElem < stackTopElement) {
          val newStack = (currElem, currIdx) :: stack
          (stackTopIdx, newStack)
        } else {
          adjustStack(currElem, currIdx, stack.tail)
        }
      }

    }

    def util(idx: Int, stack: List[(Int, Int)], res: List[Int]): Array[Int] = {

      if (idx < 0) res.toArray
      else {
        val currElem = temperatures(idx)
        val (biggerRightIdx, newStack) = adjustStack(currElem, idx, stack)
        val diff = biggerRightIdx - idx

        util(idx - 1, newStack, diff :: res)
      }

    }

    val lastIdx = temperatures.length - 1
    val lastElement = temperatures(lastIdx)
    util(temperatures.length - 2, List((lastElement, lastIdx)), List(0))

  }

  def main(args: Array[String]): Unit = {
    // [1,1,4,2,1,1,0,0]
    println(dailyTemperatures(Array(73,74,75,71,69,72,76,73)).mkString(" "))
  }

}
