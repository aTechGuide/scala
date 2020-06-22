package in.kamranali.algorithms.arrays


/**
  * https://www.interviewbit.com/problems/pascal-triangle/
  */

class PascalTriangle {

  def solve(A: Int): Array[Array[Int]]  = {

    val res = Array.fill[Array[Int]](A)(Array.empty[Int])

    if (A == 0) res
    else {

      res(0) = Array(1)

      (1 until A).foreach { idx =>

        val temp = Array.fill[Int](idx + 1)(1)

        (1 until idx + 1).foreach { innerIdx =>

          temp(innerIdx) =
            if ((innerIdx < res(idx - 1).length) && (innerIdx - 1 >= 0)) res(idx - 1)(innerIdx) + res(idx - 1)(innerIdx - 1)
            else temp(innerIdx)
        }

        res(idx) = temp
      }

      res.foreach(row => println(row.mkString(" ")))

      res
    }
  }

}

object PascalTriangle extends App {
  val sol = new PascalTriangle()

  // Test 1
  var data = sol.solve(5)

}
