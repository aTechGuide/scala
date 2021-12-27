package in.kamranali.leetcode.medium

object DiagonalTraverseII1424 {
  
  /*
  In a 2D matrix, elements in the same diagonal have same sum of their indices.
    - https://leetcode.com/problems/diagonal-traverse-ii/discuss/597741/Clean-Simple-Easiest-Explanation-with-a-picture-and-code-with-comments
  */
  def findDiagonalOrder(nums1: List[List[Int]]): Array[Int] = {
    import scala.collection.mutable.Map
    import scala.collection.immutable.Queue
    
    // Initializations
    val nums = nums1.map(l => l.toArray).toArray
    val diagMap: Map[Int, Queue[Int]] = Map()
    
    
    // Utility Function 
    
    nums.indices.foreach { r => {
      nums(r).indices.foreach { c => {
        val elem = nums(r)(c)
        val key = r + c
        
        if (diagMap.contains(key)) {
          val nQ = diagMap(key) :+ elem
          diagMap(key) = nQ
        } else {
          diagMap(key) = Queue(elem)
        }
      }}
    }}
    
    (0 until diagMap.size).foldLeft[Array[Int]](Array())((accum, key) => {
      accum ++ diagMap(key).toArray
    })
  }
  
  /*
    00 01 02
    10 11 12
    20 21 22
    
    (i - 1, j + 1)
    00
    10, 01 
    20, 11, 02
    
    21, 12
    22
  */
  def findDiagonalOrder1(nums1: List[List[Int]]): Array[Int] = {
    import scala.collection.immutable._
    
    // Initializations
    val nums = nums1.map(l => l.toArray).toArray
    
    val rows = nums.length
    val cols = nums.foldLeft[Int](-1)((accum, l) => {
      accum max l.length
    })
    
    
    // Utility Function
    def moveDiagonally(r: Int, c: Int, res: Queue[Int]): Array[Int] = {     
      if (r < 0 || c >= cols) res.toArray
      else {
        if (c < nums(r).length) {
          val elem = nums(r)(c)
          moveDiagonally(r - 1, c + 1, res :+ elem)
        } else {
          moveDiagonally(r - 1, c + 1, res)
        }
      }
    }
    
    def leftFace(r: Int, res: Array[Int]): Array[Int] = {
      
      if (r == nums.length) res.toArray
      else {
        val diagRes = moveDiagonally(r, 0, Queue())
        
        leftFace(r + 1, res ++ diagRes)
      }
    }
    
    def bottomFace(c: Int, res: Array[Int]): Array[Int] = {
      if (c == cols) res
      else {
        val diagRes = moveDiagonally(rows - 1, c, Queue())
        
        bottomFace(c + 1, res ++ diagRes)
      }
    }
    
    // Driver
    leftFace(0, Array()) ++ bottomFace(1, Array())
        
  }
}