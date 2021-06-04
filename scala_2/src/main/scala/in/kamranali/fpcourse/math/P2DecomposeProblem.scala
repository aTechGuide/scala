package in.kamranali.fpcourse.math

import scala.annotation.tailrec

// Decompose into constitute Prime Divisors
object P2DecomposeProblem extends App {

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
