package in.kamranali.leetcode.medium

object ShortestUnsortedContinuousSubarray581 {
    def findUnsortedSubarray(nums: Array[Int]): Int = {

        //iterate from end of array
        //find the last element which is bigger than the last seen min from
        //its right side and mark it as begin
        def findStartIdx(idx: Int, min: Int, res: Int = -1): Int = {

            if (idx == -1) res
            else {
                val cur = nums(idx)
                val newMin = math.min(min, cur)

                if (cur > newMin) findStartIdx(idx - 1, newMin, idx)
                else findStartIdx(idx - 1, newMin, res)

            }

        }

        //iterate from beginning of array
        //find the last element which is smaller than the last seen max from
        //its left side and mark it as end
        def findEndIdx(idx: Int, max: Int, res: Int): Int = {

            if(idx == nums.length) res
            else {
                val cur = nums(idx)
                val newMax = math.max(max, cur)

                if (cur < max) findEndIdx(idx + 1, newMax, idx)
                else findEndIdx(idx + 1, newMax, res)
            }

        }
        
        val startIdx = findStartIdx(nums.length - 2, nums.last)
        
        if (startIdx == -1) 0
        else {
            val endIdx = findEndIdx(1, nums(0), nums.length)
            // return diff
            endIdx - startIdx + 1
        }
        
    }

    def main(args: Array[String]): Unit = {

//         assert(findUnsortedSubarray(Array(2,6,4,8,10,9,15)) == 5)
//         assert(findUnsortedSubarray(Array(1,3,2,2,2)) == 4)
//        assert(findUnsortedSubarray(Array(2,3,3,2,4)) == 3)
        assert(findUnsortedSubarray(Array(2,1)) == 2)

    }
}