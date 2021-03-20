package in.kamranali.algorithms.greedy

/**
  * https://www.interviewbit.com/tutorial/activity-selection-problem/
  *
  * How greedy Algo is optimal
  * - https://www.codesdope.com/course/algorithms-activity-selection/
  * - https://www.geeksforgeeks.org/activity-selection-problem-greedy-algo-1/
  */

class ActivitySelection {

  def solve(start: Array[Int], end: Array[Int]): Array[Int]  = {

    var currentActivityIndex = 0
    var solution = Array[Int](currentActivityIndex) // Choosing first activity

    (1 until start.length).foreach{ startIterator =>

      solution = if (start(startIterator) > end(currentActivityIndex)) {
        currentActivityIndex = startIterator
        solution :+ startIterator
      } else solution
    }

    // println(solution.mkString(" "))
    solution
  }

}

object ActivitySelection extends App {
  val sol = new ActivitySelection()

  // Test 1
  var start = Array[Int](1, 3, 0, 5, 8, 5)
  var end = Array[Int](2, 4, 6, 7, 9, 9)

  var data = sol.solve(start, end)
  assert(data sameElements Array[Int](0, 1, 3, 4))

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.solve(A)
//  assert(data == 42)

}
