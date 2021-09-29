package in.kamranali.leetcode.hard

object MaximalRectangle85 {
   def largestRectangleArea(heights: Array[Int]): Int = {
        
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
                        
                        val area = popVal * (idx - (newSt.head + 1))
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
                        
                        val area = popVal * (idx - (newSt.head + 1))
                        maxArea(idx, newSt, math.max(max, area))
                    }
                }
            }
        }
        
        maxArea(0, List(), Int.MinValue)
        
    }
   def maximalRectangle(matrix: Array[Array[Char]]): Int = {
      
      val rows = matrix.length
      val cols = matrix(0).length
      
      val intMatrix = Array.ofDim[Int](rows, cols)
      
      (0 until rows).foreach { row => {
        (0 until cols).foreach { col => {
          intMatrix(row)(col) = matrix(row)(col) - '0'
        }}
      }}
      
      // convert it into histogram row
      (1 until rows).foreach { row => {
        (0 until cols).foreach { col => {
          
          if (intMatrix(row)(col) == 1) {
            intMatrix(row)(col) = intMatrix(row)(col) + intMatrix(row-1)(col)
          }
          
        }}
      }}
     
     // fow each row calculate area of histogram
     intMatrix.foldLeft[Int](0)((acc, row) => {
       val area = largestRectangleArea(row)
       math.max(acc, area)
     })
        
    }

  def main(args: Array[String]): Unit = {
    val data = Array(Array(),
      Array('0', '1', '1', '1'),
      Array('1', '1','1', '1'),
      Array('1', '1','1', '1'),
      Array('1', '1','0', '0')
    )
  }
}