package in.kamranali.fpcourse.math

import scala.util.Random

// Compute the number Pi with increasing precision
object P3ApproximatePIProblemMedium extends App {

  val random = new Random(System.currentTimeMillis)

  def approximatePI(nPoints: Int): Double = {

    val pointsInsideCircle = (1 to nPoints).map { _ =>
      val x = random.nextDouble()
      val y = random.nextDouble()

      x * x + y * y
    }.count(distance => distance < 1)

    pointsInsideCircle * 4.0 / nPoints // approximation of PI
  }

  println(approximatePI(100000000))

}
