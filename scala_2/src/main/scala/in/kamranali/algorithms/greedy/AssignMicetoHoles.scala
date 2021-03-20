package in.kamranali.algorithms.greedy

/**
  * https://www.interviewbit.com/problems/assign-mice-to-holes/
  *
  * Proof of Correctness
  * - https://www.interviewbit.com/problems/assign-mice-to-holes/ [Solution Approach]
  */

class AssignMicetoHoles {

  def mice(A: Array[Int], B: Array[Int]): Int  = {

    var max = 0
    val zipped = A.sorted.zip(B.sorted)

    for {
      (mouse, hole) <- zipped
    } {
      max = math.max(max, math.abs(mouse - hole))
    }

    println(max)
    max
  }

}

object AssignMicetoHoles extends App {
  val sol = new AssignMicetoHoles()

  // Test 1
  var A = Array[Int](-49, 58, 72, -78, 9, 65, -42, -3)
  var B = Array[Int](30, -13, -70, 58, -34, 79, -36, 27)

  var data = sol.mice(A, B)
  assert(data == 28) // 28

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.solve(A)
//  assert(data == 42)

}
