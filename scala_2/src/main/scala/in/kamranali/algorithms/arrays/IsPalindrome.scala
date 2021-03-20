package in.kamranali.algorithms.arrays

import scala.annotation.tailrec

/**
  *
  * Reference
  *  - https://www.thedigitalcatonline.com/blog/2015/04/07/99-scala-problems-06-palindome/
  */

class IsPalindrome {

  @tailrec
  final def solve[A](input: List[A]): Boolean  = input match {
    case Nil => true
    case List(a) => true
    case list => input.head == input.last && solve(input.tail.init)
  }

}

object IsPalindrome extends App {
  val sol = new IsPalindrome()
  var A: String = _
  var data: Boolean = _

  // Test 1
  A = "nitin"
  data = sol.solve[Char](A.toList)
  assert(data)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.solve(A)
//  assert(data == 42)

}
