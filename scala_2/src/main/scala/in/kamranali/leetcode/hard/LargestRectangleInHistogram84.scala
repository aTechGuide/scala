package in.kamranali.leetcode.hard

import scala.annotation.tailrec

object LargestRectangleInHistogram84 {
    def largestRectangleArea(heights: Array[Int]): Int = {
        
        @tailrec
        def maxArea(idx: Int, st: List[Int], max: Int): Int = {
            if (idx == heights.length) {
                if (st.isEmpty) max
                else {
                    val poppedIdx :: newSt = st
                    val popVal = heights(poppedIdx)

                    if (newSt.isEmpty) {
                        val area = popVal * idx
                        maxArea(idx, newSt, math.max(max, area))
                    } else {
                        val area = popVal * (idx - newSt.head - 1)
                        maxArea(idx, newSt, math.max(max, area))
                    }
                }
            } else {
                val curr = heights(idx)
                
                if (st.isEmpty || curr >= heights(st.head)) 
                    maxArea(idx + 1, idx :: st, max)
                else {

                    val poppedIdx :: newSt = st
                    val popVal = heights(poppedIdx)

                    if (newSt.isEmpty) {
                        val area = popVal * idx
                        maxArea(idx, newSt, math.max(max, area))
                    } else {

                        val area = popVal * (idx - newSt.head - 1)
                        maxArea(idx, newSt, math.max(max, area))
                    }
                }
            }
        }
        
        maxArea(0, List(), Int.MinValue)
    }

    def main(args: Array[String]): Unit = {
        println(largestRectangleArea(Array(1,8,6,2,5,4,8,3,7)))
    }
}