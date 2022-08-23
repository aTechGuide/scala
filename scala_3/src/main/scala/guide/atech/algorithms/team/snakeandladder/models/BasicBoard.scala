package guide.atech.algorithms.team.snakeandladder.models

import scala.collection.mutable

case class BasicBoard(override val size: Int, override val snakes: List[Snake], override val ladders: List[Ladder],
                      override val playerPieces: mutable.Map[Int, Int]) extends Board(size, snakes, ladders, playerPieces)
