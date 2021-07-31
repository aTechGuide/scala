package in.kamranali.leetcode.easy

object RemoveDuplicatesfromSortedArray26 {
    def removeDuplicates(nums: Array[Int]): Int = {
        
        def process(prevItr: Int, curItr: Int, count: Int): Int = {
            
            if (curItr == nums.length) count
            else {
                val prevElem = nums(prevItr)
                val currElem = nums(curItr)
                
                if (prevElem != currElem) {
                    if (prevItr + 1 == curItr) process(prevItr + 1, curItr + 1, count + 1)
                    else {
                        val temp = nums(prevItr + 1)
                        nums(prevItr + 1) = nums(curItr)
                        nums(curItr) = temp
                        
                        process(prevItr + 1, curItr + 1, count + 1)
                    }
                }
                else {
                    process(prevItr, curItr + 1, count)
                }
            }
            
        }
        
        process(0, 1, 1)
        
    }

  def main(args: Array[String]): Unit = {

    println(removeDuplicates(Array(1, 1, 2)))
  }
}