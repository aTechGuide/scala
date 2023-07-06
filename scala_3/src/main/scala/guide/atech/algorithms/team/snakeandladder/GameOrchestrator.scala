package guide.atech.algorithms.team.snakeandladder

import guide.atech.algorithms.team.snakeandladder.models.Player

import scala.collection.mutable

/*
  [Template Method Pattern]
  - Fixes the Algorithm to play the game
* */
trait GameOrchestrator[T] {

  protected def rollDice(): Int
  protected def isPlayerWon(player: T): Boolean
  protected def movePlayer(player: T, move: Int): Unit
  protected def isGameComplete: Boolean
  protected def completeGame(): Unit
  protected def getCurrentPlayer: T

  protected def releaseCurrentPlayer(player: T): Unit

  // Game Algorithm Finalized using Template Pattern
  final def startGame(): Unit = {

    while (!isGameComplete) {
      val diceValue = rollDice()
      val currentPlayer = getCurrentPlayer

      movePlayer(currentPlayer, diceValue)

      if (isPlayerWon(currentPlayer)) {
        println(s"Player $currentPlayer Won")
        completeGame()
      } else {
        releaseCurrentPlayer(currentPlayer)
      }
    }
  }

}
