package guide.atech.algorithms.team.tic_tac_toe

import munit.FunSuite

class TicTacToeImplTest extends FunSuite {

  test("Player 1 should win") {
    val ttt = new TicTacToeImpl(3)

    val player1 = 1
    val player2 = 2

    assert(!ttt.makeMove(0, 0, player1))
    assert(!ttt.makeMove(0, 1, player1))
    assert(ttt.makeMove(0, 2, player1))
  }

}
