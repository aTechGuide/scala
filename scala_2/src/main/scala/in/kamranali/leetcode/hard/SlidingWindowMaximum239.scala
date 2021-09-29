package in.kamranali.leetcode.hard

object SlidingWindowMaximum239 {
    def maxSlidingWindow(nums: Array[Int], k: Int): Array[Int] = {
      import scala.collection.mutable
      import scala.collection.immutable.Queue

    def util(idx: Int, dq: mutable.ArrayDeque[Int], ans: Queue[Int]): Array[Int] = {

      if (idx == nums.length) ans.toArray
      else if (dq.isEmpty) {
        if (idx < k - 1) {
          util(idx + 1, dq.append(idx), ans)
        } else {
          dq.append(idx)
          val newAns = ans.enqueue(nums(dq.head))  // Window is sliding so let's find the ans; ans is always at the front of dq
          util(idx + 1, dq, newAns)
        }
      }
      else if (idx < k - 1) {
        val curr = nums(idx)
        val last = nums(dq.last)

        if (curr < last) {
          // current is < last element, just append it
          dq.append(idx)
          util(idx + 1, dq, ans)
        } else {
          // remove the last element and check
          dq.removeLast()
          util(idx, dq, ans)
        }
      }
      else {
        val curr = nums(idx)

        // Correct the Front Part of Queue
        if (dq.head <= idx - k) {
          // remove first and recurse
          dq.remove(0)
          util(idx, dq, ans)
        } else {
          // Append the element at its correct position
          val last = nums(dq.last)

          if (curr < last) {
            // current is < last element, just append it
            dq.append(idx)
            val newAns = ans.enqueue(nums(dq.head))  // Window is sliding so let's find the ans; ans is always at the front of dq
            util(idx + 1, dq, newAns)
          } else {
            // remove the last element and check
            dq.removeLast()
            util(idx, dq, ans)
          }
        }
      }
    }

    if (nums.length == 1) nums
    else if (k == 1) nums
    else util(0, mutable.ArrayDeque(), Queue())
  }

  def main(args: Array[String]): Unit = {
    println(maxSlidingWindow(Array(1,3,-1,-3,5,3,6,7), 3).mkString(" "))
  }
}