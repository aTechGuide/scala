package in.kamranali.algorithms.dp

/**
  * https://www.interviewbit.com/problems/longest-arithmetic-progression/
  * Same Method
  * - https://www.youtube.com/watch?v=Lm38EAoDc7c
  */

class LongestArithmeticProgression {

  def solve(A: Array[Int]): Int  = {

    if (A.length == 1) 1
    else if (A.length == 2) 2
    else {
      var memory = Map.empty[Int, Map[Int, Int]]
      memory = memory + (0 -> Map.empty[Int, Int])

      var maxVal = 0

      (1 until A.length).foreach { idx =>
        val current = A(idx)
        memory = memory + (idx -> Map.empty[Int, Int])

        (0 until idx).foreach { inner =>
          val diff = current - A(inner)

          val factor =
            if (memory(inner).contains(diff)) memory(inner)(diff) + 1
            else 2

          var data = memory(idx)
          data = data + (diff -> factor)

          memory = memory - idx
          memory = memory + (idx -> data)

          maxVal = math.max(maxVal, factor)
        }
      }

      maxVal
    }
  }

}

object LongestArithmeticProgression extends App {
  val sol = new LongestArithmeticProgression()

  // Test 1
  var A = Array[Int](3, 6, 9, 12)
  var data = sol.solve(A)
  assert(data == 4)

  // Test 2
  A = Array[Int](9, 4, 7, 2, 10)
  data = sol.solve(A)
  assert(data == 3)

  // Test 3
  A = Array[Int](1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
  data = sol.solve(A)
  assert(data == 10)

}
