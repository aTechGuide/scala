package in.kamranali.leetcode.medium

import scala.annotation.tailrec

object Subsets78 {
  
  def subsets(nums: Array[Int]): List[List[Int]] = {

    var ans: List[List[Int]] = List(List())

    def backtracking(position: Int, temp: List[Int]): Unit = {

      for {
        choice <- position until nums.length
      } {

        val elem = nums(choice)
        val nTemp = temp :+ elem

        // add it to the answer
        ans = ans :+ nTemp

        println(s"bt called with position = $position choicee = $choice temp = $temp nTemp = $nTemp elem = $elem")
        backtracking(choice + 1, nTemp)
      }
    }

    backtracking(0, List())
    ans
  }
  
  def subsets1(nums: Array[Int]): List[List[Int]] = {
    
    /*
    Cascading Approach
      - {}
      - {} {1} -- adding 1
      - {} {1} {2} {1 2} -- adding 2
      - {} {1} {2} {1 2} {3} {1 3} {2 3} {1 2 3} -- adding 3
    */
    @tailrec
    def generate(idx: Int, prev: List[List[Int]]): List[List[Int]] = {
      if (idx == nums.length) prev
      else {
        val cur = nums(idx)
        
        val nextRow = prev.map { elem =>
          elem :+ cur
        }
        
        generate(idx + 1, prev ++ nextRow)
      }
    }
    
    generate(0, List(List()))
  }

  def main(args: Array[String]): Unit = {
    println(subsets(Array(1, 2)))
  }
}