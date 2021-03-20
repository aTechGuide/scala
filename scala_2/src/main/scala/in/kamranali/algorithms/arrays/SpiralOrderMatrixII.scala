package in.kamranali.algorithms.arrays

/**
  * https://www.interviewbit.com/problems/spiral-order-matrix-ii/
  */

class SpiralOrderMatrixII {

  def generateMatrix(A: Int): Array[Array[Int]]  = {

    val ret = Array.ofDim[Int](A, A)

    var rs = 0
    var re = A

    var cs = 0
    var ce = A

    var i = 1


    while ((rs <= re) && (cs <= ce)) {

      (cs until ce).foreach { idx =>
        ret(rs)(idx) = i
        i += 1
      }

      rs += 1 // Top row is done

      (rs until re).foreach { idx =>
        ret(idx)(ce - 1) = i
        i += 1
      }

      ce -= 1 // Right Column is Done

      ((ce - 1) to cs by -1).foreach { idx =>
        ret(re - 1)(idx) = i
        i += 1
      }

      re -= 1 // Bottom row is done


      ((re - 1) to rs by -1).foreach { idx =>
        ret(idx)(cs) = i
        i += 1
      }

      cs += 1 // Left Column is Done

    }

    // ret.foreach {row => println(row.mkString(" "))}

    ret
  }

}

object SpiralOrderMatrixII extends App {
  val sol = new SpiralOrderMatrixII()

  // Test 1
  var data = sol.generateMatrix(4)
  // assert(data == 42)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.generateMatrix(A)
//  assert(data == 42)

}
