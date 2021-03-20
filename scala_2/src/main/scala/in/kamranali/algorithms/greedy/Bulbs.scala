package in.kamranali.algorithms.greedy

/**
  * https://www.interviewbit.com/problems/bulbs/
  */

class Bulbs {

  def bulbs(A: Array[Int]): Int  = {

    var counter = 0
    var toggled = false

    for (elem <- A) {

      toggled = if ((elem == 0) && !toggled) {
        counter += 1
        !toggled
      }
      else if ((elem == 1) && toggled) {
        counter += 1
        !toggled
      } else toggled
    }

    counter
  }

  /**
    *
    * Fastest Sol in Interview
    */
  def bulbsOptim(A: Array[Int]): Int  = {
    var counter = 0
    for (state <- A) {
      if ((counter + state) % 2 == 0) {
        counter += 1
      }
    }
    counter
  }
}

object Bulbs extends App {
  val sol = new Bulbs()

  // Test 1
  var A = Array[Int](0, 1, 0, 1)
  var data = sol.bulbs(A)
  assert(data == 4)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.solve(A)
//  assert(data == 42)

}
