package guide.atech.algorithms.team.snakeandladder.models

import scala.collection.mutable

trait Board(val size: Int, val snakes: List[Snake], val ladders: List[Ladder], val playerPieces: mutable.Map[Int, Int])