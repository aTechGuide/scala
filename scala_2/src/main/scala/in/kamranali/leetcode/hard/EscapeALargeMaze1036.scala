package in.kamranali.leetcode.hard

object EscapeALargeMaze1036 {
  /*
    Solution
    - https://happygirlzt.com/code/1036.html / https://www.youtube.com/watch?v=HuYnIDHXqMM
    - https://leetcode.com/problems/escape-a-large-maze/discuss/971283/Just-a-description-of-the-solution-NO-CODE
    - 
  */
  def isEscapePossible(blocked: Array[Array[Int]], source: Array[Int], target: Array[Int]): Boolean = {
    import scala.collection.mutable
    
    val blockedSet: Set[String] = blocked.map(elem => elem(0).toString + "->" + elem(1).toString).toSet
    val directions: Array[(Int, Int)] = Array(0 -> 1, 0 -> -1, 1 -> 0, -1 -> 0)
    
    // Auxiliary Function
    def isValid(x: Int, y: Int): Boolean = (x >=0 && x < 1000000) && (y >= 0 && y < 1000000)

    def dfs(x: Int, y: Int, source: Array[Int], target: Array[Int], seen: mutable.Set[String]): Boolean = {
      if (x == target(0) && y == target(1)) true
      else if ( math.abs(source(0) - x) + math.abs(source(1) - y) > 200) true
      else if (!isValid(x, y)) false
      else if (seen.contains(x.toString + "->" + y.toString)) false
      else if (blockedSet.contains(x.toString + "->" + y.toString)) false
      else {
        // Try all four directions in search of target
        val key = x.toString + "->" + y.toString

        seen.add(key)

        directions.foldLeft[Boolean](false) ((accum, elem) => {
          accum || dfs(x + elem._1, y + elem._2, source, target, seen)
        })
      }
    }
    
    dfs(source(0), source(1), source, target, mutable.Set()) && dfs(target(0), target(1), target, source, mutable.Set())
  }

  def main(args: Array[String]): Unit = {
    println(isEscapePossible(Array(Array(0,3), Array(1,0), Array(1,2)), Array(0,0), Array(0,2)))
  }
}