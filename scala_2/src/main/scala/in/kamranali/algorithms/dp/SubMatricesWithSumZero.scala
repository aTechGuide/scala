package in.kamranali.algorithms.dp

/**
  * https://www.interviewbit.com/problems/sub-matrices-with-sum-zero/
  */

class SubMatricesWithSumZero {

  def SubArraysWithZeroSum(A: Array[Int]): Int = {

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


  def solve(A: Array[Array[Int]]): Int  = {

    var count = 0

    val rows = A.length
    val cols = A(0).length

    var memory = Array.fill[Int](rows)(0)

    (0 until cols).foreach { L =>

      memory = Array.fill[Int](rows)(0)

      (L until cols).foreach { R =>

        // Running Row Sum
        (0 until rows).foreach { row =>
          memory(row) = memory(row) + A(row)(R)
        }

        val tempSum = SubArraysWithZeroSum(memory)
        count += tempSum

      }
    }

    count
  }

}

object SubMatricesWithSumZero extends App {
  val sol = new SubMatricesWithSumZero()

  // Test 1
  var A = Array.ofDim[Int](3, 3)
  A(0) = Array(-8, 5, 7)
  A(1) = Array(3, 7, -8)
  A(2) = Array(5, -8, 9)

  var data = sol.solve(A)
  assert(data == 2)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.solve(A)
//  assert(data == 42)

}
