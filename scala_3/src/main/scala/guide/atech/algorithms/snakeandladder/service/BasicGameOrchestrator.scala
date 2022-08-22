package guide.atech.algorithms.snakeandladder.service

import guide.atech.algorithms.snakeandladder.models.{BasicBoard, Board, Player}

import scala.annotation.tailrec
import scala.collection.mutable

class BasicGameOrchestrator(board: Board, val players: mutable.Queue[Player]) extends GameOrchestrator[Player] {

  var gameCompleted = false

  @tailrec
  private def getNewPositionAfterGoingThroughSnakesAndLadders(position: Int): Int = {

    val matchedSnake = board.snakes.filter(snake => snake.start == position)
    val matchedLadder = board.ladders.filter(board => board.start == position)

    if (matchedSnake.nonEmpty) {
      val snake = matchedSnake.head
      println(s"Bitten by a snake at ${snake.start} moving to ${snake.end}")
      getNewPositionAfterGoingThroughSnakesAndLadders(snake.end)
    } else if (matchedLadder.nonEmpty) {
      val ladder = matchedLadder.head
      println(s"Climbed a ladder at ${ladder.start} moving to ${ladder.end}")
      getNewPositionAfterGoingThroughSnakesAndLadders(ladder.end)
    } else position
  }

  override protected def movePlayer(player: Player, move: Int): Unit = {
    val oldPosition = board.playerPieces(player.id)
    val newTentativePosition = move + oldPosition

    val newPosition =
      if (newTentativePosition > board.size) oldPosition
      else getNewPositionAfterGoingThroughSnakesAndLadders(newTentativePosition)

    board.playerPieces.put(player.id, newPosition)

    println(s"Player ${player.id} rolled and got dice = $move and moved from $oldPosition to $newPosition")
  }

  override protected def rollDice(): Int = DiceService.roll

  override protected def isPlayerWon(player: Player): Boolean = board.playerPieces(player.id) == board.size

  override protected def isGameComplete: Boolean = gameCompleted

  override protected def completeGame(): Unit = {
    gameCompleted = true
  }
  override protected def getCurrentPlayer: Player = players.dequeue()

  override protected def releaseCurrentPlayer(player: Player): Unit = players.enqueue(player)
}
