package in.kamranali.leetcode.hard

import scala.collection.mutable.ArrayBuffer

/**
  *
  * Reference
  * - https://www.youtube.com/watch?v=wGbuCyNpxIg
  */

object NQueens51 {

  def solveNQueens(N: Int): List[List[String]] = {

    def isValid(latestQueenRow: Int, placement: Vector[Int]): Boolean = {

      val latestQueenColumn = placement(latestQueenRow)
      (0 until latestQueenRow).foreach { currentRow =>

        // Check Column Difference between the idx Queen and the latest Queen i.e.
        val colDiff = math.abs(placement(currentRow) - latestQueenColumn)

        // diff == 0 => Same Column
        // diff == rows - idx => Diagonal [column difference == rows difference]
        if (colDiff == 0 || colDiff == latestQueenRow - currentRow) return false
      }
      true
    }

    def convert(value: Array[Int]): List[String] = {
      value.foldLeft[List[String]](List())((accum, elem) => {
        val template = Array.fill[String](N)(".")
        template(elem) = "Q"

        accum :+ template.mkString("")
      })
    }

    def solveUtil(row: Int, currentPlacement: Array[Int], result: ArrayBuffer[List[String]]): Unit = {

      // Base Case [We have exhausted the board]
      if (row == N) result.addOne(convert(currentPlacement))
      else {

        // In current row, We will check all the columns
        (0 until N).foreach { col =>
          currentPlacement(row) = col // Choose that Column
          if (isValid(row, currentPlacement.toVector)) { // Check if it satisfies the constraints
            solveUtil(row + 1, currentPlacement, result) // Recurse for that selection

          }
          currentPlacement(row) = -1 // Undo that choice
          // Do NOT do currentPlacement -= col; else it removes the first occurrence of `col` element
        }
      }
    }

    val result = ArrayBuffer[List[String]]()
    solveUtil(0, Array.fill[Int](N)(-1), result)
    result.toList
  }

  def main(args: Array[String]): Unit = {
    println(solveNQueens(4))
  }

}
