package guide.atech.algorithms.team.snakeandladder

import guide.atech.algorithms.team.snakeandladder.models.{BasicBoard, Ladder, Player, Snake}
import guide.atech.algorithms.team.snakeandladder.service.BasicGameOrchestrator

import scala.collection.mutable

object SnakeAndLadderGameDriver {
  def main(args: Array[String]): Unit = {
    val snakes = List(Snake(19, 3))
    val ladders = List(Ladder(5, 15))

    val players = mutable.Queue(
      Player("kamran", 1),
      Player("ali", 2)
    )

    val pieces = mutable.Map(1 -> 0, 2 -> 0)
    val board = BasicBoard(20, snakes, ladders, pieces)

    val service = new BasicGameOrchestrator(board, players)

    println(s"Game Started with ${players.size} players")
    service.startGame()

    println(s"Game Ended")
  }
}
