package in.kamranali.algorithms.arrays


/**
  * https://www.geeksforgeeks.org/range-sum-queries-without-updates/
  */

class RangeSumQuery(input: Array[Int]) {

  private val processed = preProcessInput(input)

  private def preProcessInput(input: Array[Int]): Array[Int] = {

    if (input.isEmpty) Array.empty[Int]
    else {

      val memory = Array.fill[Int](input.length)(0)

      memory(0) = input(0)
      (1 until input.length).foreach {idx =>
        memory(idx) = memory(idx - 1) + input(idx)
      }

      memory
    }
  }

  def solve( start: Int, end: Int): Int  = {

    println(this.processed.mkString(" "))

    if (start == 0) processed(end)
    else processed(end) - processed(start - 1)

  }

}

object RangeSumQuery extends App {

  var A = Array[Int](1, 2, 3, 4, 5)
  val sol = new RangeSumQuery(A)

  // Test 1

  var data = sol.solve(1, 2)
  assert(data == 5)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.solve(A)
//  assert(data == 42)

}
