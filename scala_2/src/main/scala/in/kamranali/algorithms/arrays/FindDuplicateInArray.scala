package in.kamranali.algorithms.arrays

/**
  * https://www.interviewbit.com/problems/find-duplicate-in-array/
  */

import scala.collection.mutable.ArrayBuffer

class Solution {
  def repeatedNumber(A: Array[Int]): Int  = {

    var memory = Set[Int]()

    for (elem <- A) {

      if (memory.contains(elem)) return elem
      else {
        memory = memory + elem
      }

    }
    -1
  }

}

object Sol extends App {

  val sol = new Solution()

  val A = ArrayBuffer[Int](3, 4, 1, 4, 1)

  val res = sol.repeatedNumber(A.toArray)
  println(res)

}

