package guide.atech.misc

import java.util.concurrent.ThreadLocalRandom
import scala.util.Random

object RandomNumberBetweenRange {

  /**
   *
   * @param min : Inclusive
   * @param max : Exclusive
   * @return Random Number
   *         Ref: https://www.baeldung.com/java-generating-random-numbers-in-range
   */
  def randomNumber(min: Int, max: Int): Int = {
    min + new Random().nextInt(max - min)
  }

  // https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
  def randomNumberMultiThreaded(min: Int, max: Int): Int = {
    ThreadLocalRandom.current().nextInt(min, max)
  }
}
