package in.kamranali.algorithms.arrays

/**
  * https://www.youtube.com/watch?v=RlXtTF34nnE
  *
  * Similar
  * - https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/submissions/
  */



class CountOccurrencesOfKinSortedArray {

  sealed trait Occurrence
  case object firstOccurrence extends Occurrence
  case object lastOccurrence extends Occurrence

  def searchRange(nums: Array[Int], target: Int): Int  = {

    def binarySearch(A: Array[Int], elem: Int, occurrence: Occurrence): Int = {

      var first = 0
      var last = A.length - 1

      while (first <= last) {

        val mid = (last - first)/2 + first
        val key = A(mid)

        if (key == elem) {

          if ((occurrence == firstOccurrence) && (mid - 1 > -1) && key == A(mid - 1)) {
            last = mid - 1
          } else if ((occurrence == lastOccurrence) && (mid + 1 < A.length) && key == A(mid + 1)) {
            first = mid + 1
          } else {
            return mid
          }

        } else if (key < elem) {
          // Go right
          first = mid + 1
        } else {
          // Go left
          last = mid - 1
        }

      }

      -1
    }

    if (nums.length == 0) 0
    else {
      val first = binarySearch(nums, target, firstOccurrence)

      if (first == -1) -1
      else {
        val last = binarySearch(nums, target, lastOccurrence)
        last - first + 1
      }
    }
  }

}

object CountOccurrencesOfKinSortedArrayTest extends App {
  val sol = new CountOccurrencesOfKinSortedArray()

  // Test 1
  var A = Array[Int](1, 1, 1, 2, 3)
  var data = sol.searchRange(A, 1)
  assert(data == 3)

  // Test 2
  A = Array[Int](1, 2, 3, 4, 4, 4)
  data = sol.searchRange(A, 3)
  assert(data == 1)

  // Test 3
  A = Array[Int]()
  data = sol.searchRange(A, 1)
  assert(data == 0)

}

