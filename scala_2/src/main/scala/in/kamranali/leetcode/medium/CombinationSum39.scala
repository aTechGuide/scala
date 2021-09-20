package in.kamranali.leetcode.medium

import scala.collection.immutable.{List, Queue}

object CombinationSum39 {
  /*
    Logs for visualization

    Trying for ArrayBuffer(2)
    Trying for ArrayBuffer(2, 2)
    Trying for ArrayBuffer(2, 2, 2)
    Trying for ArrayBuffer(2, 2, 2, 2)
    Trying for ArrayBuffer(2, 2, 2, 3)
    Trying for ArrayBuffer(2, 2, 2, 6)
    Trying for ArrayBuffer(2, 2, 2, 7)
    Trying for ArrayBuffer(2, 2, 3)
    answer found
    Trying for ArrayBuffer(2, 2, 6)
    Trying for ArrayBuffer(2, 2, 7)
    Trying for ArrayBuffer(2, 3)
    Trying for ArrayBuffer(2, 3, 3)
    Trying for ArrayBuffer(2, 3, 6)
    Trying for ArrayBuffer(2, 3, 7)
    Trying for ArrayBuffer(2, 6)
    Trying for ArrayBuffer(2, 7)
    Trying for ArrayBuffer(3)
    Trying for ArrayBuffer(3, 3)
    Trying for ArrayBuffer(3, 3, 3)
    Trying for ArrayBuffer(3, 3, 6)
    Trying for ArrayBuffer(3, 3, 7)
    Trying for ArrayBuffer(3, 6)
    Trying for ArrayBuffer(3, 7)
    Trying for ArrayBuffer(6)
    Trying for ArrayBuffer(6, 6)
    Trying for ArrayBuffer(6, 7)
    Trying for ArrayBuffer(7)
    answer found
   */
  def combinationSum(candidates: Array[Int], target: Int): List[List[Int]] = {
    import scala.collection.mutable.ArrayBuffer

    val len = candidates.length
    def util(startChoice: Int, sum: Int, temp: ArrayBuffer[Int], res: ArrayBuffer[List[Int]]): Unit = {
      if (sum > target) ()
      else if (sum == target) {
        println(s"answer found")
        res.addOne(temp.toList)
      }
      else {

        /*
        In combination sequence of numbers do NOT matter hence [2 2 3] and [2 3 2] are both same having sum = 7.
        To avoid similar sequence, we place the items as follows
          - If first position starts with choice 0 then next positions choices start with 0
          - If first position starts with choice 1 then next positions choices start with 1
         */
        for (choice <- startChoice until len) {
          // Choose
          val elem = candidates(choice)
          temp.addOne(elem)

          println(s"Trying for $temp")
          //recurse
          // The same number may be chosen from candidates an unlimited number of times.
          // hence we aren't doing choice + 1
          util(choice, sum + elem, temp, res)

          // backtrack
          temp.remove(temp.length - 1)

        }
      }
    }

    val res = ArrayBuffer[List[Int]]()
    util(0, 0, ArrayBuffer[Int](), res)
    res.toList

  }

  // https://www.youtube.com/watch?v=OyZFFqQtu98
  def combinationSum1(candidates: Array[Int], target: Int): List[List[Int]] = {
    val len = candidates.length

    def util(idx: Int, sum: Int, temp: Queue[Int]): List[List[Int]] = {
      if (idx == len) List()
      else if (sum > target) List()
      else if (sum == target) List(temp.toList)
      else {

        val elem = candidates(idx)

        val chooseElem = util(idx, sum + elem, temp :+ elem)
        val notChooseElem = util(idx + 1, sum, temp)

        chooseElem ++ notChooseElem
      }
    }

    util(0, 0, Queue())
  }

  def main(args: Array[String]): Unit = {
    println(combinationSum(Array(2, 3, 6, 7), 7))
  }
}