package in.kamranali.fpcourse.various

import scala.annotation.tailrec

object NQueens {

  def nQueens(n: Int): List[List[Int]] = {

    def conflict(col: Int, queens: List[Int]): Boolean = {

      // queen is the col position and index is the row Queen is in
      def conflictOneQueen(col: Int, queenCol: Int, queenRow: Int): Boolean = {
        queenCol == col || (queenRow + 1) == (col - queenCol) || (queenRow + 1) == (queenCol - col)
      }

      queens.zipWithIndex.exists {
        case (queenCol, queenRow) => conflictOneQueen(col, queenCol, queenRow)
      }
    }

    /*
      ._._._._.
      |_|x|_|_|
      |_|_|_|_|
      |_|_|_|_|
      |_|_|_|_|
      nQueens(4)
      nqt(0, [], []) =
      nqt(0, [0], []) =
      nqt(1, [0], []) =
      nqt(2, [0], []) =
      nqt(0, [2,0], []) =
      nqt(1, [2,0], []) =
      nqt(2, [2,0], []) =
      nqt(3, [2,0], []) =
      nqt(4, [2,0], []) =
      nqt(3, [0], []) =
      nqt(0, [3,0], []) =
      nqt(1, [3,0], []) =
      nqt(0, [1,3,0], []) =
      nqt(1, [1,3,0], []) =
      nqt(2, [1,3,0], []) =
      nqt(3, [1,3,0], []) =
      nqt(4, [1,3,0], []) =
      nqt(2, [3,0], []) =
      nqt(3, [3,0], []) =
      nqt(4, [3,0], []) =
      nqt(4, [0], [])
      nqt(1, [], [])
      ._._._._.
      |_|x|_|_|
      |_|_|_|x|
      |x|_|_|_|
      |_|_|x|_|
      [1,3,0,2]
      ._._._._.
      |_|_|x|_|
      |x|_|_|_|
      |_|_|_|x|
      |_|x|_|_|
      [2,0,3,1]
     */
    @tailrec
    def nQueensTailrec(col: Int, queenColPosition: List[Int], sol: List[List[Int]]): List[List[Int]] = {

      // I'm out of options
      if (col >= n && queenColPosition.isEmpty) sol
      // I'm out of options on THIS row; move the previous queen by 1
      else if (col >= n) nQueensTailrec(queenColPosition.head + 1, queenColPosition.tail, sol)
      // conflict with the other queens, try next position
      else if (conflict(col, queenColPosition)) nQueensTailrec(col + 1, queenColPosition, sol)
      else if (queenColPosition.length == n-1) {
        // I've just built a solution
        val newSol = col :: queenColPosition
        nQueensTailrec(col + 1, queenColPosition, newSol :: sol)
      } else {
        // try next queen on the next row, as this one is valid
        nQueensTailrec(0, col :: queenColPosition, sol)
      }
    }

    nQueensTailrec(0, List(), List())

  }

  def prettyPrint(solution: List[Int], n: Int): String = {
    val topEdge = (1 to n).map(_ => "_").mkString(".", ".", ".") // ._._._._.
    val rows = solution.map { queen =>
      val cellsBefore = (0 until queen).map(_ => "_")
      val beforeString = if (cellsBefore.isEmpty) "|" else cellsBefore.mkString("|", "|", "|")
      val cellsAfter = ((queen + 1) until n).map(_ => "_")
      val afterString = if (cellsAfter.isEmpty) "|" else cellsAfter.mkString("|", "|", "|")

      beforeString + "x" + afterString
    }

    s"$topEdge\n${rows.mkString("\n")}"
  }

  def main(args: Array[String]): Unit = {
    val ans = nQueens(4)


    val visual = ans.map( row => prettyPrint(row, 4))
    visual.foreach(println)
  }


}
