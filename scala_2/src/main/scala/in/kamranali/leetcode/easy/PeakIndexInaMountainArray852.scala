package in.kamranali.leetcode.easy

object PeakIndexInaMountainArray852 {
  def peakIndexInMountainArray(arr: Array[Int]): Int = {
    
    def bs(l: Int, r: Int): Int = {
      
      if (l == r) l // only one element left in array
      else if (l + 1 == r) {
        // Two elements left in array
        if (arr(l) > arr(r)) l else r
      } else {
        val mid = ((r - l) / 2) + l
        
        if (arr(mid) > arr(mid - 1) && arr(mid) > arr(mid + 1)) mid
        else if (arr(mid) > arr(mid - 1) && arr(mid) < arr(mid + 1)) {
          // we are in increasing sequence go right
          bs(mid + 1, r)
        } else {
          bs(l, mid - 1)
        }
      }
    }
    
    bs(0, arr.length - 1)
    
  }
  
  def peakIndexInMountainArray1(arr: Array[Int]): Int = {

    def bs(left: Int, right: Int): Int = {

      if (left < right) {
        val mid = ((right - left) / 2) + left
        if (arr(mid) < arr(mid + 1)) bs(mid + 1, right)
        else bs(left, mid)
      } else right
    }

    bs(0, arr.length - 1)
  }

  def main(args: Array[String]): Unit = {
    println(peakIndexInMountainArray(Array(3,4,5,1)))
  }
}