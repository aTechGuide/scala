package in.kamranali.fpcourse.math

import scala.annotation.tailrec
import scala.util.Random

object NumberOps {
  implicit class RRichInt(n: Int) {
    def isPrime: Boolean = isPrimeProblem.isPrime(n)
    def decompose: List[Int] = decomposeProblem.decompose(n)
  }
}

object NumberOpsTest extends App {

  import NumberOps._

  println(3.isPrime)
  println(15.decompose)
}

/**
  * Problems
  */
// Check whether number is a Prime
object isPrimeProblem extends App {

  // Complexity = O(sqrt(N))
  def isPrime(n: Int): Boolean = {

    @tailrec
    def isPrimeTailrec(currentDivisor: Int): Boolean = {

      if (currentDivisor > math.sqrt(math.abs(n))) true
      else if (n % currentDivisor == 0) false
      else isPrimeTailrec(currentDivisor + 1)
    }

    if (n == 0 || n == 1 || n == -1) false
    else if (n == 2) true
    else isPrimeTailrec(2)
  }


  assert(!isPrime(0))
  assert(!isPrime(1))
  assert(isPrime(2))
  assert(isPrime(3))
  assert(!isPrime(4))
  assert(isPrime(5))
  assert(isPrime(7))
  assert(isPrime(13))
  assert(!isPrime(15))
  assert(isPrime(2003))
  assert(isPrime(-2003))
  assert(isPrime(2731189))
  assert(!isPrime(517935871))
}

// Decompose into constitute Prime Divisors
object decomposeProblem extends App {

  // Complexity = O(sqrt(N))
  def decompose(n: Int): List[Int] = {
    assert(n > 0)
    @tailrec
    def decomposeTailrec(remaining: Int, currentDivisor: Int, accum: List[Int]): List[Int] = {

      if (currentDivisor > math.sqrt(remaining)) remaining :: accum
      else if (n % currentDivisor == 0)
        decomposeTailrec(remaining / currentDivisor, currentDivisor, currentDivisor :: accum)
      else decomposeTailrec(remaining, currentDivisor + 1, accum)
    }

    decomposeTailrec(n, 2, List.empty[Int])
  }

  assert(decompose(11) == List(11))
  assert(decompose(15) == List(5, 3))
  assert(decompose(16) == List(2, 2, 2, 2))
}

// Compute the number Pi with increasing precision
object approximatePIProblem extends App {
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

