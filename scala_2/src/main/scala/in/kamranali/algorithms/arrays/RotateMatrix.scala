package in.kamranali.algorithms.arrays


/**
  * https://www.interviewbit.com/problems/rotate-matrix/
  */

class RotateMatrix {

  def rotate(A: Array[Array[Int]]): Array[Array[Int]]  = {

    A.foreach(row => println(row.mkString(" ")))

    val iter = math.ceil(A(0).length / 2).toInt
    val length = A(0).length

    (0 to iter).foreach { iter =>

      val temp_1 = A(iter)(length - 1)
      A(iter)(length - 1) = A(0)(iter)

      val temp_2 = A(length - 1)(length - 1 - iter)
      A(length - 1)(length - 1 - iter) = temp_1

      val temp_3 = A(length - 1 - iter)(0)
      A(length - 1 - iter)(0) = temp_2


      A(0)(iter) = temp_3
    }

    println()
    A.foreach(row => println(row.mkString(" ")))
    A
  }

}

object RotateMatrix extends App {
  val sol = new RotateMatrix()

  // Test 1
//  var A = Array.ofDim[Int](3,3)
//  A(0) = Array(1,2,3)
//  A(1) = Array(4,5,6)
//  A(2) = Array(7,8,9)

  var A = Array.ofDim[Int](3,3)
  A(0) = Array(1,2,3)
  A(1) = Array(4,5,6)
  A(2) = Array(7,8,9)


  var data = sol.rotate(A)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.rotate(A)
//  assert(data == 42)

}
