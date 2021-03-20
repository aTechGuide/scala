package in.kamranali.algorithms.backtracking

/**
  * Time: O( n * n!)
  *   - n! -> As we have n possibilities for 1st slot, (n - 1) possibilities for second slot ....
  *   - n -> Linear amount of work to copy strings to answer
  *
  * Space
  * - O( n * n!) -> If we return the answer in the array
  * - O (n) -> If we are just printing the answer, O (n) space is taken by the Call stack
  *
  * Methods
  * - Normal BT -> Choose / Explore / Unchoose
  * - BT -> Passing the Substrings to next call
  *
  * Reference
  * - https://www.mathplanet.com/education/algebra-2/discrete-mathematics-and-probability/permutations-and-combinations
  * - https://www.youtube.com/watch?v=GCm7m5671Ps
  */

class PermuteAString {

  def permute(nums: Array[Int]): List[List[Int]] = {
    import scala.collection.mutable.ArrayBuffer

    def bt(input: Array[Int], selected: Array[Boolean], pos: Int, candidate: Array[Int], result: ArrayBuffer[List[Int]]): Unit = {

      if (pos == input.length) result.append(candidate.toList)
      else {

        input.indices.foreach { idx =>

          if (!selected(idx)) {

            val elem = input(idx)

            // Choose
            selected(idx) = true
            candidate(pos) = elem

            // Explore
            bt(input, selected, pos + 1, candidate, result)

            // Unchoose
            selected(idx) = false
            candidate(pos) = -1

          } else {
            // Do Nothing for this
          }
        }
      }

    }

    val res = ArrayBuffer[List[Int]]()
    val sel = Array.fill[Boolean](nums.length)(false)
    val cand = Array.fill[Int](nums.length)(-1)

    bt(nums, sel, 0, cand, res)

    res.toList
  }

}

object PermuteAString extends App {
  val sol = new PermuteAString()

  // Test 1
  var A = Array(1, 2, 3, 4)
  var data = sol.permute(A)
  println(data)
  println(data.length)
//  assert(data == 42)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.solve(A)
//  assert(data == 42)

}

