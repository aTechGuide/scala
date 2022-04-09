package in.kamranali.leetcode.medium

// Sample List(1, 4, 3, 2), List(2, 1, 3, 4)
object NextPermutation31 {
    // https://www.youtube.com/watch?v=6qXO72FkqwM
    def nextPermutation(nums: Array[Int]): Unit = {

        val len = nums.length
        def findPeak(idx: Int): Int = {
            if (idx == 0) idx
            else {
                if (nums(idx) > nums(idx - 1)) idx
                else findPeak(idx - 1)
            }
        }

        def swap(l: Int, r: Int): Unit = {
            val temp = nums(l)
            nums(l) = nums(r)
            nums(r) = temp
        }

        def sort(l: Int, r: Int): Unit = {
            if (l >= r) ()
            else {
                swap(l, r)
                sort(l + 1, r - 1)
            }
        }

        def findSwap(border: Int, idx: Int): Int = {
            if (idx == border) border
            else {
                if (nums(idx) > nums(border - 1)) idx
                else findSwap(border, idx - 1)
            }
        }
        val peakIdx = findPeak(len - 1)

        if (peakIdx == 0) {
            // Edge case (5, 4, 3, 2)
            sort(0, len - 1)
        } else {
            // search an idx, such that num(idx) > num(peakIdx - 1)
            val swapIDx = findSwap(peakIdx, len - 1)

            // and swap it
            swap(peakIdx - 1, swapIDx)

            // sort
            sort(peakIdx, len - 1)
        }
    }

    def main(args: Array[String]): Unit = {
        val data1 = Array(1, 4, 3, 2)
        nextPermutation(data1)

        val data2 = Array(5, 4, 3, 2)
        nextPermutation(data2)

        println(data1.mkString(" ")) // List(2, 1, 3, 4)
        println(data2.mkString(" ")) // List(2, 3, 4, 5)
    }
}