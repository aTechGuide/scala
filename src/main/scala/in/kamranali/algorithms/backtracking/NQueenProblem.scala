package in.kamranali.algorithms.backtracking

/**
  *
  * Reference
  * - https://www.youtube.com/watch?v=wGbuCyNpxIg
  */

class NQueenProblem {

  case class CurrentPlacement(var placement: Array[Int])
  case class Result(var value: List[Array[Int]])


  def solve(N: Int): List[Array[Int]]  = {

    def isValid(rowsSoFar: Int, placement: Array[Int]): Boolean = {

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

    def solveUtil(N: Int, row: Int, currentPlacement: CurrentPlacement, result: Result): Unit = {

      // Base Case [We have exhausted the board]
      if (row == N) result.value = result.value :+ currentPlacement.placement.clone()
      else {

        // In current row, We will check all the columns
        (0 until N).foreach { col =>
          currentPlacement.placement(row) = col // Choose that Column
          if (isValid(row, currentPlacement.placement)) { // Check if it satisfies the constraints
            solveUtil(N, row + 1, currentPlacement, result) // Recurse for that selection

          }
          currentPlacement.placement(row) = 0 // Undo that choice
        }
      }
    }


    val placement = CurrentPlacement(Array.fill[Int](N)(0))
    val result = Result(List[Array[Int]]())

    solveUtil(N, 0, placement, result)
    result.value

  }

}

object NQueenProblemTest extends App {
  val sol = new NQueenProblem()

  // Test 1
  var A = 4
  var data = sol.solve(A)
  data.foreach { array =>
    println(array.mkString(" "))
  }


//  assert(data == 42)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.solve(A)
//  assert(data == 42)

}
