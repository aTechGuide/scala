package in.kamranali.fpcourse.math

object NumberOps {
  implicit class RRichInt(n: Int) {
    def isPrime: Boolean = P1isPrimeProblem.isPrime(n)
    def decompose: List[Int] = P2DecomposeProblem.decompose(n)
  }
}

object NumberOpsTest extends App {

  import NumberOps._

  println(3.isPrime)
  println(15.decompose)
}

