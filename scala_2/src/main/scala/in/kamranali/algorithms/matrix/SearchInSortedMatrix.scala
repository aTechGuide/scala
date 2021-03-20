package in.kamranali.algorithms.matrix

/**
  * Reference
  *
  * - https://www.youtube.com/watch?v=FOa55B9Ikfg&t=1585s
  */

class SearchInSortedMatrix {

  /**
    * Time -> O(m + n)
    * @param A
    * @param item
    * @return
    */
  def search(A: Array[Array[Int]], item: Int): Boolean  = {

    var row = A.length - 1
    var col = 0

    while (row > -1 && col < A(0).length) {

      if (A(row)(col) == item) return true
      else if (A(row)(col) > item) row = row - 1 // Go Left
      else col += 1 // Go Right
    }
    false
  }

  /**
    * Search in Sorted Matrix where First element of subsequent Row is > last element of current Row
    *
    * Time -> O(log(mn))
    *
    * @param A
    * @param item
    * @return
    */
  def searchSpecialSortedArray(A: Array[Array[Int]], item: Int): Boolean  = {

    var cols = A(0).length
    val rows = A.length

    var start = 0
    var end = rows * cols - 1

    while (start <= end) {

      val mid: Int = (end - start) / 2 + start

      val row = mid / cols
      val col = mid % cols

      val value = A(row)(col)

      if (value == item) return true // Found
      else if (value < item) start = mid + 1 // Search Right Half
      else end = mid - 1 // Search Left Half

    }

    false
  }

}

object SearchInSortedMatrix extends App {
  val sol = new SearchInSortedMatrix()

  // Test 1
  var A = Array.ofDim[Int](3, 4)
  A(0) = Array[Int](1, 4, 7, 11)
  A(1) = Array[Int](8, 9, 10, 20)
  A(2) = Array[Int](11, 12, 17, 30)

  assert(sol.search(A, 10))
  assert(sol.search(A, 1))
  assert(sol.search(A, 30))
  assert(sol.search(A, 11))
  assert(!sol.search(A, 37))

}

object SearchInSortedMatrixWithSpecialSorted extends App {
  val sol = new SearchInSortedMatrix()

  // Test 1
  var A = Array.ofDim[Int](3, 4)
  A(0) = Array[Int](0, 1, 2, 3)
  A(1) = Array[Int](4, 5, 6, 7)
  A(2) = Array[Int](8, 9, 10, 11)

  assert(sol.searchSpecialSortedArray(A, 6))
  assert(sol.searchSpecialSortedArray(A, 7))
  assert(!sol.searchSpecialSortedArray(A, 22))

}

