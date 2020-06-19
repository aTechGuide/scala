package in.kamranali.algorithms.arrays

/**
  * https://www.interviewbit.com/problems/add-one-to-number/
  */
class AddOneToNumber {

  def plusOne(A: Array[Int]): Array[Int]  = {

    var increment = 1

    for (i <- A.length - 1 to 0 by -1) {

      val sum = increment + A(i)

      if (sum == 10) {
        A(i) = 0
      } else {
        A(i) = A(i) + increment
        increment = 0
      }
    }

    val res = if (increment == 0) A else increment +: A // In case of 999
    println(res.mkString(" "))

    res.dropWhile(_ == 0) // Drop initial zeros
  }
}

object Test extends App {


  val sol = new AddOneToNumber()

  // Test 1
  var A = Array[Int](0, 1,2,3)
  var data = sol.plusOne(A)
  assert(data.sameElements(Array[Int](1,2,4)))

  // Test 2
  A = Array[Int](0, 1,9,9)
  data = sol.plusOne(A)
  assert(data.sameElements(Array[Int](2,0,0)))

  // Test 3
  A = Array[Int](0,9,9)
  data = sol.plusOne(A)
  assert(data.sameElements(Array[Int](1,0,0)))
}
