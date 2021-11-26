package in.kamranali.leetcode.hard

object CountOfSmallerNumbersAfterSelf315 {
  /*
    - Counting inversions in an array
    - https://www.youtube.com/watch?v=owZhw-A0yWE
    - My merge Sort https://leetcode.com/problems/sort-an-array/
  */
  // Helpers
  case class ArrayValueWithIndex(var value: Int, idx: Int)

  // Actual API
  def countSmaller(nums: Array[Int]): List[Int] = {

    // Initializations
    val numsWithIdx = nums.zipWithIndex.map(elem => ArrayValueWithIndex(elem._1, elem._2))
    val result = Array.fill[Int](nums.length)(0)

    def merge(l: Int, mid: Int, r: Int): Unit = {

      // Initializations
      val len = r - l + 1
      val merged = Array.fill[ArrayValueWithIndex](len)(ArrayValueWithIndex(-1,-1))

      // Auxiliary functions
      def sortedMerge(idx: Int, leftPos: Int, rightPos:Int, numElemsRightArrayLessThanLeftArray: Int): Unit = {

        if (leftPos == (mid + 1) && rightPos == (r + 1)) () // Done
        else if (leftPos == (mid + 1)) {
          // left side done just copy right side elements

          merged(idx) = numsWithIdx(rightPos)
          sortedMerge(idx + 1, leftPos, rightPos + 1, numElemsRightArrayLessThanLeftArray)
        } else if (rightPos == (r + 1) ) {
          // right side done just copy left side elements

          merged(idx) = numsWithIdx(leftPos)
          // update result when we are traversing left Position
          result(numsWithIdx(leftPos).idx) += numElemsRightArrayLessThanLeftArray

          sortedMerge(idx + 1, leftPos + 1, rightPos, numElemsRightArrayLessThanLeftArray)
        } else {

          if (numsWithIdx(leftPos).value > numsWithIdx(rightPos).value) {

            merged(idx) = numsWithIdx(rightPos)
            sortedMerge(idx + 1, leftPos, rightPos + 1, numElemsRightArrayLessThanLeftArray + 1)
          } else {
            merged(idx) = numsWithIdx(leftPos)

            // update result when we are traversing left Position
            result(numsWithIdx(leftPos).idx) += numElemsRightArrayLessThanLeftArray
            sortedMerge(idx + 1, leftPos + 1, rightPos, numElemsRightArrayLessThanLeftArray)
          }
        }
      }

      def copyElements(tempIdx: Int, numsIdx: Int): Unit = {

        if (tempIdx == len) ()
        else {
          numsWithIdx(numsIdx) = merged(tempIdx)
          copyElements(tempIdx + 1, numsIdx + 1)
        }
      }

      // create a sorted array in temp
      sortedMerge(0, l, mid + 1, 0)

      // copy the elements from result array to original array
      copyElements(0, l)

    }

    def mergeSort(l: Int, r: Int): Unit = {

      if (l == r) ()
      else {
        val mid = ((r - l) / 2) + l
        mergeSort(l, mid)
        mergeSort(mid + 1, r)

        merge(l, mid, r)
      }
    }

    // Driver Program
    mergeSort(0, numsWithIdx.length - 1)
    result.toList
  }

  def main(args: Array[String]): Unit = {
    println(countSmaller(Array(5,2,6,1)))
  }
}