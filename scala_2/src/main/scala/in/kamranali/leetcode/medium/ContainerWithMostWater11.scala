package in.kamranali.leetcode.medium

object ContainerWithMostWater11 {
    def maxArea(height: Array[Int]): Int = {
        
        def find(l: Int, r: Int, area: Int): Int = {
            
            if (l >= r) area
            else {
                val width = r - l
                val lV = height(l)
                val rV = height(r)
                
                val vertical = math.min(lV, rV)
                val newArea = width * vertical
                
                if (lV < rV) {
                    
                    find(l + 1, r, math.max(area, newArea))
                } else {
                    find(l, r - 1, math.max(area, newArea))
                }
            }
        }
        
        find(0, height.length - 1, Int.MinValue)
    }

  def main(args: Array[String]): Unit = {
    println(maxArea(Array(1,8,6,2,5,4,8,3,7)))
  }
}