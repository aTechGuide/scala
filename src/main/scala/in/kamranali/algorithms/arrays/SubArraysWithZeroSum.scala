package in.kamranali.algorithms.arrays

/**
  * Sub problem of -> https://www.interviewbit.com/problems/sub-matrices-with-sum-zero/
  */

class SubArraysWithZeroSum {

  def solve(A: Array[Int]): Int = {

    var sumSoFar = 0
    var count = 0

    var memory = Map.empty[Int, Int]

    A.foreach { value =>

      sumSoFar += value

      if (sumSoFar == 0) count += 1

      if (memory.contains(sumSoFar)) {
        count = count + memory(sumSoFar)

        val tempCount = memory(sumSoFar)

        memory -= sumSoFar
        memory += (sumSoFar -> (tempCount + 1))
      } else {
        memory += (sumSoFar -> 1)
      }
    }

    count
  }

}

object SubArraysWithZeroSum extends App {
  val sol = new SubArraysWithZeroSum()

  // Test 1
  var A = Array[Int](1, -1, 2, -2, 3, -3, 4, -4)
  var data = sol.solve(A)
  assert(data == 10)

  // Test 2s
  A = Array[Int](1, -1, -2, 2)
  data = sol.solve(A)
  assert(data == 3)

  // Test 3
    A = Array[Int](1, 2, 3, -3, -2, -1)
    data = sol.solve(A)
    assert(data == 3)

}
