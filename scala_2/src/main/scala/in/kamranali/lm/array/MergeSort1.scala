package in.kamranali.lm.array

/*
    https://leetcode.com/problems/sort-an-array/

    [LM Array Chapter 1] Implementation Of Sorting Algorithms
    https://logicmojo.com/sub_videos/38
 */
object MergeSort1 {
    def sortArray(nums: Array[Int]): Array[Int] = {
      
      def merge(l: Int, mid: Int, r: Int): Unit = {
        
        val len = r - l + 1
        val temp = Array.fill[Int](len)(0)
        
        def sortedMerge(idx: Int, i: Int, j:Int): Unit = {
          
          if (i == (mid + 1) && j == (r + 1)) () // Done
          else if (i == (mid + 1)) {
            // left side done just copy right side elements
            
            temp(idx) = nums(j)
            sortedMerge(idx + 1, i, j + 1)
          } else if (j == (r + 1) ) {
            // right side done just copy left side elements
            
            temp(idx) = nums(i)
            sortedMerge(idx + 1, i + 1, j)
          } else {
            
            if (nums(i) < nums(j)) {
              
              temp(idx) = nums(i)
              sortedMerge(idx + 1, i + 1, j)
            } else {
              
              temp(idx) = nums(j)
              sortedMerge(idx + 1, i, j + 1)
            }
          }
        }
        
        def copyElements(tempIdx: Int, numsIdx: Int): Unit = {
          
          if (tempIdx == len) ()
          else {
            nums(numsIdx) = temp(tempIdx)
            copyElements(tempIdx + 1, numsIdx + 1)
          }
        }
        
        // create a sorted array in temp
        sortedMerge(0, l, mid + 1)
        
        // copy the elements from temp array to original array
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
      
      mergeSort(0, nums.length - 1)
      nums
    }

  def main(args: Array[String]): Unit = {
    println(sortArray(Array(5, 4, 3, 2, 1)).mkString(" "))
  }
}