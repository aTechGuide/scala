package guide.atech.algorithms.team.snakeandladder.service

import scala.util.Random

object DiceService {

  def roll: Int = Random.nextInt(6) + 1
}
