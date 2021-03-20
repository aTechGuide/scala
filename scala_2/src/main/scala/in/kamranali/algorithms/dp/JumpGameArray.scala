package in.kamranali.algorithms.dp


/**
  * https://www.interviewbit.com/problems/jump-game-array/
  */

class JumpGameArray {

  def canJump(A: Array[Int]): Int  = {

    val memory = Array.fill[Boolean](A.length)(false)
    memory(0) = true

    (1 until A.length).foreach { idx =>

      var j = idx - 1
      while (j >=0) {

        if (memory(j) && A(j) >= (idx - j)) {
          memory(idx) = true
          j = -1
        }
        j -= 1
      }
      if (!memory(idx)) return 0
    }

    if (memory.last) 1
    else 0
  }

  def canJumpOpt(A: Array[Int]): Int  = {

    val memory = Array.ofDim[Int](A.length)
    memory(0) = A(0)

    if (memory(0) == 0 && A.length != 1) 0
    else {
      (1 until A.length).foreach {idx =>
        memory(idx) = math.max(memory(idx -1) - 1, A(idx)) // Memorise Max Jump possible from this point

        if (memory(idx) == 0 && idx != A.length - 1) return 0
      }
      1
    }
  }

}

object JumpGameArray extends App {
  val sol = new JumpGameArray()

  // Test 1
  var A = Array[Int](2, 3, 1, 1, 4)
  var data = sol.canJump(A)
  assert(data == 1)

  // Test 2
  A = Array[Int](3, 2, 1, 0, 4)
  data = sol.canJump(A)
  assert(data == 0)

}
