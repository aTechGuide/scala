package in.kamranali.algorithms.backtracking

import scala.collection.mutable.ArrayBuffer

/**
  *
  * Reference
  * - https://www.youtube.com/watch?v=wGbuCyNpxIg
  */

class NQueenProblem {

  def solve(N: Int): ArrayBuffer[Vector[Int]]  = {

    def isValid(rowsSoFar: Int, placement: Vector[Int]): Boolean = {

      val latestQueen = placement(rowsSoFar)
      (0 until rowsSoFar).foreach { idx =>

        // Check Column Difference between the idx Queen and the latest Queen i.e.
        val colDiff = math.abs(placement(idx) - latestQueen)

        // diff == 0 => Same Column
        // diff == rows - idx => Diagonal [column difference == rows difference]
        if (colDiff == 0 || colDiff == rowsSoFar - idx) return false
      }
      true
    }

    def solveUtil(N: Int, row: Int, currentPlacement: ArrayBuffer[Int], result: ArrayBuffer[Vector[Int]]): Unit = {

      // Base Case [We have exhausted the board]
      if (row == N) result += currentPlacement.toVector
      else {

        // In current row, We will check all the columns
        (0 until N).foreach { col =>
          currentPlacement += col // Choose that Column
          if (isValid(row, currentPlacement.toVector)) { // Check if it satisfies the constraints
            solveUtil(N, row + 1, currentPlacement, result) // Recurse for that selection

          }
          currentPlacement.remove(currentPlacement.length - 1) // Undo that choice
          // Do NOT do currentPlacement -= col; else it removes the first occurrence of `col` element
        }
      }
    }


    val placement = ArrayBuffer[Int]()
    val result = ArrayBuffer[Vector[Int]]()

    solveUtil(N, 0, placement, result)
    result

  }

}

object NQueenProblem extends App {
  val sol = new NQueenProblem()

  // Test 1
  var A = 4
  var data = sol.solve(A)

  // 1 3 0 2
  // 2 0 3 1
  data.foreach { array =>
    println(array.mkString(" "))
  }
}
