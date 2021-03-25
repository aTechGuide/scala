package in.kamranali.fpcourse.strings

import scala.annotation.tailrec

/*
  n == 1, ()
  n == 2, ()(), (())
  n == 3, ()()(), ()(()), (())(), ((())), (()())

  Leetcode -> https://leetcode.com/problems/generate-parentheses/
 */

object GenerateValidParenthesis extends App {

  // Back tracking Approach
  def generateAllValidParenthesis(n: Int): List[String] = {
    def tailRec(open: Int, close: Int, temp: String, ans: List[String]): List[String] = {

      if (close == n) temp :: ans
      else if (open == n) tailRec(open, close + 1, temp + ")", ans)
      else if (open == close) tailRec(open + 1, close, temp + "(", ans)
      else tailRec(open + 1, close, temp + "(", ans) ++ tailRec(open, close + 1, temp + ")", ans)
    }

    tailRec(0, 0, "", List())
  }

  // Checking
//  println(generateAllValidParenthesis(1))
//  println(generateAllValidParenthesis(2))
//  println(generateAllValidParenthesis(3))

  def generateAllValidParenthesisDaniel(n: Int): List[String] = {

    @tailrec
    def tailRec(remaining: Int, currentStrings: Set[String]): Set[String] = {
      if (remaining == 0) currentStrings
      else {
        val newStrings = for {
          string <- currentStrings
          index <- 0 until string.length
        } yield {
          val (before, after) = string.splitAt(index)
          s"$before()$after"
        }

        tailRec(remaining - 1, newStrings)
      }

    }

    assert(n >= 0)

    if (n == 0) List()
    else tailRec(n-1, Set("()")).toList
  }

  // Checking
  println(generateAllValidParenthesisDaniel(1))
  println(generateAllValidParenthesisDaniel(2))
  println(generateAllValidParenthesisDaniel(3))
}
