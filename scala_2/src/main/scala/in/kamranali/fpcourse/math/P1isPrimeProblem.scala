package in.kamranali.fpcourse.math

import scala.annotation.tailrec

// Check whether number is a Prime
object P1isPrimeProblem extends App {

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
