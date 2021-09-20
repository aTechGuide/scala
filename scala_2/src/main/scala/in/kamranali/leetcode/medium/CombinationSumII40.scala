package in.kamranali.leetcode.medium

import scala.collection.immutable._

object CombinationSumII40 {

  // https://www.youtube.com/watch?v=G1fRTGRxXU8
  def combinationSum2(candidates: Array[Int], target: Int): List[List[Int]] = {

    val sorted = candidates.sorted
    val len = candidates.length
    var res: List[List[Int]] = List()

    /*
      evaluating Position = 0, choice = 0, elem = 2, newSum = 2, newSol = Queue(2)
      evaluating Position = 1, choice = 1, elem = 2, newSum = 4, newSol = Queue(2, 2)
      evaluating Position = 2, choice = 2, elem = 3, newSum = 7, newSol = Queue(2, 2, 3)
      Dead end found for Position = 3, so skipping choices
      evaluating Position = 1, choice = 2, elem = 3, newSum = 5, newSol = Queue(2, 3)
      Answer found for Position = 3, so skipping choices
      SKIPPING for Position = 0, choice = 1, elem = 2, newSum = 2, newSol = Queue(2)
      evaluating Position = 0, choice = 2, elem = 3, newSum = 3, newSol = Queue(3)
      List(List(2, 3))
     */
    def util(position: Int, sum: Int, tempSol: Queue[Int]): Unit = {

      if (sum == target) {
        println(s"Answer found for Position = $position, so skipping choices")
        res = res :+ tempSol.toList
      } else if (sum > target) {
        println(s"Dead end found for Position = $position, so skipping choices")
      } else {
        for (choice <- position until len) {
          val elem = sorted(choice)
          val newSol = tempSol :+ elem
          val newTempSum = sum + elem

          if (choice > position && sorted(choice) == sorted(choice - 1)) {
            println(s"SKIPPING for Position = $position, choice = $choice, elem = $elem, newSum = $newTempSum, newSol = $newSol")
          } else {
            println(s"evaluating Position = $position, choice = $choice, elem = $elem, newSum = $newTempSum, newSol = $newSol")

            // Each number in candidates may only be used once in the combination.
            // Hence choice + 1
            util(choice + 1, newTempSum, newSol)
          }
        }
      }
    }

    util(0, 0, Queue())
    res
  }

  /*
    This solution times out in leetcode

    In below solution I'm not Skipping the invalid choices, to do so I've to keep a pointer for them as done in
    combinationSum2 Solution
   */
  def combinationSum3(candidates: Array[Int], target: Int): List[List[Int]] = {
    val len = candidates.length
    val sorted = candidates.sorted

    def util(idx: Int, sum: Int, temp: Queue[Int]): Set[List[Int]] = {
      if (idx == len)
        if (sum == target) {
          println(s"[idx == len] Solution found for idx = $idx, sum = $sum, temp = $temp")
          Set(temp.toList)
        }
        else {
          //println(s"[idx == len] Dead end found for idx = $idx, sum = $sum, temp = $temp")
          Set()
        }
      else if (sum > target) {
        //println(s"[sum > target] Dead end found for idx = $idx, sum = $sum, temp = $temp")
        Set()
      }
      else if (sum == target) {
        println(s"Solution found for idx = $idx, sum = $sum, temp = $temp")
        Set(temp.toList)
      }
      else {
        val elem = sorted(idx)
        val newSum = sum + elem
        val newTemp = temp :+ elem

        println(s"Choosing idx = $idx, newSum = $newSum, newTemp = $newTemp")
        val chooseElem = util(idx + 1, newSum, newTemp)

        //println(s"NOT Choosing idx = $idx, sum = $sum, temp = $temp")
        val notChooseElem = util(idx + 1, sum, temp)

        chooseElem ++ notChooseElem
      }
    }

    util(0, 0, Queue()).toList
  }

  def main(args: Array[String]): Unit = {
    println(combinationSum3(Array(2, 2, 3), 5))
    //println(combinationSum2(Array(2, 2, 3), 5))
  }

}
