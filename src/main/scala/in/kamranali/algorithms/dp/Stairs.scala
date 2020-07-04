package in.kamranali.algorithms.dp

/**
  * https://www.interviewbit.com/problems/stairs/
  */

class Stairs {

  def climbStairs(A: Int): Int  = {

    if (A == 1) 1
    else if (A == 2) 2
    else {
      val memory = Array.fill[Int](A + 1)(0)
      memory(1) = 1
      memory(2) = 2

      (3 to A).foreach { idx =>
        memory(idx) = memory(idx - 1) + memory(idx - 2)
      }
      memory(A)
    }
  }

}

object Stairs extends App {
  val sol = new Stairs()

  // Test 1
  var data = sol.climbStairs(3)
  assert(data == 3)

  // Test 2
  data = sol.climbStairs(4)
  assert(data == 5)

}
