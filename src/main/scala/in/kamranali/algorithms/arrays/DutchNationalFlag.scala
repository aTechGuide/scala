package in.kamranali.algorithms.arrays

/**
  * Reference
  * - https://www.geeksforgeeks.org/sort-an-array-of-0s-1s-and-2s/
  * - https://www.youtube.com/watch?v=ER4ivZosqCg
  */

class DutchNationalFlag {

  def solve(A: Array[Int]): Array[Int]  = {

    var l, m = 0
    var h = A.length - 1

    while ( m <= h) {
      val elem = A(m)
      elem match {
        case 1 => m += 1
        case 0 => val temp = A(l); A(l) = A(m); A(m) = temp; l += 1; m += 1
        case 2 => val temp = A(h); A(h) = A(m); A(m) = temp; h -= 1
      }
    }

    // println(A.mkString(" "))
    A
  }

}

object DutchNationalFlagTest extends App {
  val sol = new DutchNationalFlag()

  // Test 1
  var A = Array[Int](0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1)
  var data = sol.solve(A)
  assert(data sameElements Array[Int](0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2))

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.solve(A)
//  assert(data == 42)

}

