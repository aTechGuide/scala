package in.kamranali.algorithms.arrays

/**
  * - https://www.geeksforgeeks.org/find-subarray-with-given-sum/
  * - https://www.geeksforgeeks.org/find-subarray-with-given-sum-in-array-of-integers/
  *
  */

class SubArrayWithGivenSum {

  def solveWithPositivesOnly(A: Array[Int], sum: Int): (Int, Int)  = {

    var left, tempSum = 0

    A.indices.foreach { idx =>

      if (tempSum + A(idx) == sum) {
        return (left, idx)
      }
      else if ( tempSum + A(idx) < sum) {
        tempSum += A(idx)
      } else {
        while (tempSum + A(idx) > sum && left < idx) {
          tempSum = tempSum - A(left)
          left += 1
        }

        tempSum += A(idx)
        if (tempSum == sum) {
          return (left, idx)
        }
      }
    }

    (-1, -1)
  }

  def solveWithNegatives(A: Array[Int], sum: Int): (Int, Int)  = {

    var memory = Map.empty[Int, Int]
    var sumSoFar = 0

    A.zipWithIndex.foreach { case (value, idx) =>

        sumSoFar += value

        if (sumSoFar == sum) return (0, idx)
        else if (memory.contains(sumSoFar - sum)) return (memory(sumSoFar - sum) + 1 , idx)

        memory = memory + (sumSoFar -> idx)
    }
    (-1, -1)

  }

}

object SubArrayWithGivenSum extends App {
  val sol = new SubArrayWithGivenSum()

  // Test 1
  var A = Array[Int](1, 4, 20, 3, 10, 5)
  var sum = 33
  var data = sol.solveWithPositivesOnly(A, sum)
  assert(data == (2, 4))

  // Test 2
  A = Array[Int](1, 4, 0, 0, 3, 10, 5)
  sum = 7
  data = sol.solveWithPositivesOnly(A, sum)
  assert(data == (1, 4))

  // Test 3
  A = Array[Int](1, 4)
  sum = 0
  data = sol.solveWithPositivesOnly(A, sum)
  assert(data == (-1, -1))

}

object SubArrayWithGivenSumHandlingNegatives extends App {
  val sol = new SubArrayWithGivenSum()

  // Test 1
  var A = Array[Int](1, 4, 20, 3, 10, 5)
  var sum = 33
  var data = sol.solveWithNegatives(A, sum)
  assert(data == (2, 4))

  // Test 2
  A = Array[Int](1, 4, 0, 0, 3, 10, 5)
  sum = 7
  data = sol.solveWithNegatives(A, sum)
  assert(data == (1, 4))

  // Test 3
  A = Array[Int](1, 4)
  sum = 0
  data = sol.solveWithNegatives(A, sum)
  assert(data == (-1, -1))

  // Test 4
  A = Array[Int](10, 2, -2, -20, 10)
  sum = -10
  data = sol.solveWithNegatives(A, sum)
  assert(data == (0, 3))

}
