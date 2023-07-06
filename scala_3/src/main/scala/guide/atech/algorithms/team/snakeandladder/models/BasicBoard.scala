package guide.atech.algorithms.team.snakeandladder.models

import scala.collection.mutable

case class BasicBoard(size: Int, snakes: List[Snake], ladders: List[Ladder], playerPieces: mutable.Map[Int, Int])