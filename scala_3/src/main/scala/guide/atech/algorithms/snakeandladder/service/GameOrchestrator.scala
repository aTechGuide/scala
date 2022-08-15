package guide.atech.algorithms.snakeandladder.service

import guide.atech.algorithms.snakeandladder.models.Player

import scala.collection.mutable

/*
  [Template Method Pattern]
  - Fixes the Algorithm to play the game
* */
trait GameOrchestrator[T](val players: mutable.Queue[T]) {

  protected def rollDice(): Int
  protected def isPlayerWon(player: T): Boolean
  protected def movePlayer(player: T, move: Int): Unit
  protected def isGameComplete: Boolean
  protected def completeGame(): Unit

  // Game Algorithm Finalized using Template Pattern
  final def startGame(): Unit = {



    while (!isGameComplete) {
      val diceValue = rollDice()
      val currentPlayer = players.dequeue()

      movePlayer(currentPlayer, diceValue)

      if (isPlayerWon(currentPlayer)) {
        println(s"Player ${currentPlayer} Won")
        completeGame()
      } else {
        players.enqueue(currentPlayer)
      }
    }
  }

}
