package guide.atech.algorithms.team.tic_tac_toe

trait TicTacToe {

  /**
   * 
   * @param x
   * @param y
   * @param player
   * @return true for Winning Move else false
   */
  def makeMove(x: Int, y: Int, player: Int): Boolean
}
