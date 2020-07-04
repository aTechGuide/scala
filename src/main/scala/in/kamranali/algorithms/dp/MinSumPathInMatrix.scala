package in.kamranali.algorithms.dp

/**
  * https://www.interviewbit.com/problems/min-sum-path-in-matrix/
  */

class MinSumPathInMatrix {

  def minPathSum(A: Array[Array[Int]]): Int  = {

//    A.foreach {row => println(row.mkString(" "))}

    val row = A.length
    val col = A(0).length
    val memory = Array.ofDim[Int](row, col)

    (0 until row).foreach { row =>
      (0 until col).foreach { col =>
        memory(row)(col) =
          if (row == 0 && col == 0) A(row)(col)
          else if (row == 0) memory(row)(col - 1) + A(row)(col)
          else if (col == 0) memory(row - 1)(col) + A(row)(col)
          else math.min(memory(row - 1)(col), memory(row)(col - 1)) + A(row)(col)
      }
    }

//    memory.foreach {row => println(row.mkString(" "))}
//    println(memory(row-1)(col-1))

    memory(row-1)(col-1)
  }

}

object MinSumPathInMatrix extends App {
  val sol = new MinSumPathInMatrix()

  // Test 1
  var A = Array.ofDim[Int](3,3)
  A(0) = Array(1,3,2)
  A(1) = Array(4,3,1)
  A(2) = Array(5,6,1)

  var data = sol.minPathSum(A)
  assert(data == 8)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.solve(A)
//  assert(data == 42)

}
