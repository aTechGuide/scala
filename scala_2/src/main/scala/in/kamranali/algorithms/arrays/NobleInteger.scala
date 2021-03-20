package in.kamranali.algorithms.arrays

/**
  *  https://www.interviewbit.com/problems/noble-integer/
  */
class NobleInteger {

  def solve(A: Array[Int]): Int  = {

    val sorted = A.sorted

    for ( i <- 0 to sorted.length - 2 ) {

      if (sorted(i) == sorted(i + 1)) {}
      else if (sorted(i) == sorted.length - (i + 1)) return 1
    }

    if (sorted(sorted.length - 1) == 0) {
      return 1
    }
     -1
  }

}

object NobleIntegerTest extends App {

  val sol = new NobleInteger()

  // Test 1
  var A = Array[Int](-4, -2, 0, -1, -6)
  var data = sol.solve(A)
  assert(data == 1)

  // Test 2
  A = Array[Int](1,2,3,4,5,6)
  data = sol.solve(A)
  assert(data == 1)

}
