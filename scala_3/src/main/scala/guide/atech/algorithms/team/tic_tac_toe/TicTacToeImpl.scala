package guide.atech.algorithms.team.tic_tac_toe

class TicTacToeImpl(boardSize: Int) extends TicTacToe {

  private val rowCount = Array.ofDim[Int](boardSize)
  private val colCount = Array.ofDim[Int](boardSize)

  private var dCount = 0
  private var adCount = 0

  /*
  Ref:
    - https://aaronice.gitbook.io/lintcode/data_structure/design-tic-tac-toe#o-n-time-o-n-2-space-solution

  TODO
    - Ensure we are not overriding moves
    - Ensure game is played fairly e.g. chance is alternating between two players
  */
  override def makeMove(x: Int, y: Int, player: Int): Boolean = {
    val incrementer = if(player == 1) 1 else -1
    val targetSum = if(player == 1) boardSize else -boardSize

    if(checkDiagonal(x, y)) {
      dCount = dCount + incrementer
    }

    if (checkAntiDiagonal(x, y)) {
      adCount = adCount + incrementer
    }

    rowCount(x) = rowCount(x) + incrementer
    colCount(y) = colCount(y) + incrementer

    if(dCount == targetSum || adCount == targetSum || rowCount(x) == targetSum || colCount(y) == targetSum) true
    else false

  }

  private def checkDiagonal(x: Int, y: Int): Boolean = x == y

  private def checkAntiDiagonal(x: Int, y: Int): Boolean = x + y + 1 == boardSize
}
