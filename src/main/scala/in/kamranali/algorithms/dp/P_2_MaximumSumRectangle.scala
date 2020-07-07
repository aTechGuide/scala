package in.kamranali.algorithms.dp

/**
  *
  * Notes in Algorithms -> DP
  *
  * Reference
  *
  * - https://www.youtube.com/watch?v=-FgseNO-6Gk
  */
class P_2_MaximumSumRectangle {

  def maxSubArray(A: Array[Int]): Int  = {

    var sum = 0
    var maxSum = A.head

    for ( elem <- A) {
      sum += elem

      maxSum = Math.max(maxSum, sum)
      sum = Math.max(sum, 0) //<- If sum is < 0 reset to Zero`
    }

    maxSum
  }

  def maxSubArrayWithIndex(A: Array[Int]): (Int, Int, Int)  = {

    var sum = 0
    var maxSum = A.head

    var L = 0
    var R = 0

    var tempL = 0

    A.indices.foreach { idx =>

      sum += A(idx)

      if (sum >= 0) {
        if (sum > maxSum) {
          maxSum = sum
          L = tempL
          R = idx
        }
      } else {
        tempL = idx + 1
        sum = 0
      }
    }

    (maxSum, L, R)
  }

  def solve(A: Array[Array[Int]]): Int  = {

    var maxSum = A(0)(0)

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

        val temp = maxSubArray(memory)
        maxSum = math.max(maxSum, temp)

        }
      }

    maxSum
  }

  def solveWithIndex(A: Array[Array[Int]]): (Int, Int, Int, Int, Int)  = {

    var maxSum = A(0)(0)

    var sol = (0, 0, 0, 0, 0) // Left, Right, Top, Bottom

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

        val (tempSum, tempTop, tempBottom) = maxSubArrayWithIndex(memory)

        if (tempSum > maxSum) {
          maxSum = tempSum
          sol = (maxSum, L, R, tempTop, tempBottom)
        }
        maxSum = math.max(maxSum, tempSum)

      }
    }
    sol
  }

}

object P_2_MaximumSumRectangle extends App {
  val sol = new P_2_MaximumSumRectangle()

  // Test 1
  var A = Array.ofDim[Int](4, 5)

  A(0) = Array(6, -5, -7, 4, -4)
  A(1) = Array(-9, 3, -6, 5, 2)
  A(2) = Array(-10, 4, 7, -6, 3)
  A(3) = Array(-8, 9, -3, 3, -7)


  var data = sol.solve(A)
  assert(data == 17)

}

object P_2_MaximumSumRectangleWithIndex extends App {
  val sol = new P_2_MaximumSumRectangle()

  // Test 1
  var A = Array.ofDim[Int](4, 5)

  A(0) = Array(6, -5, -7, 4, -4)
  A(1) = Array(-9, 3, -6, 5, 2)
  A(2) = Array(-10, 4, 7, -6, 3)
  A(3) = Array(-8, 9, -3, 3, -7)


  var data = sol.solveWithIndex(A)
  assert(data == (17,1,2,2,3))

}