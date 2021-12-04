package in.kamranali.leetcode.medium

object MaxAreaOfIsland695 {
  /*

    - https://leetcode.com/problems/max-area-of-island/solution/150090 (Leetcode Solution first comment link)

    Simply mark each visited coordinate/position of grid with 0 so that you don't explore its adjacent coordinates    again and don't consider it as land. No need for another data struct to keep track of visited coordinates.        Here's my answer in Python 3.

  Time -> O(M * N)

  */
  def maxAreaOfIsland(grid: Array[Array[Int]]): Int = {

    val rows = grid.length
    val cols = grid(0).length

    // Auxiliary functions
    def expand(r: Int, c: Int): Int = {
      if (r < 0 || r >= rows || c < 0 || c >= cols) 0
      else {
        val elem = grid(r)(c)

        if (elem == 1) {

          // mark as visited
          grid(r)(c) = 0

          val up = expand(r + 1, c)
          val down = expand(r - 1, c)
          val left = expand(r, c - 1)
          val right = expand(r, c + 1)

          up + down + left + right + 1

        } else 0
      }
    }

    var maxArea = 0

    // Driver
    (0 until rows).foreach { row => {
      (0 until cols).foreach { col => {

        if (grid(row)(col) == 1) {
          val area = expand(row, col)
          maxArea = area max maxArea
        }
      }}
    }}

    maxArea
  }

  def main(args: Array[String]): Unit = {
    val area =
    Array(
      Array(0,0,1,0,0,0,0,1,0,0,0,0,0),
      Array(0,0,0,0,0,0,0,1,1,1,0,0,0),
      Array(0,1,1,0,1,0,0,0,0,0,0,0,0),
      Array(0,1,0,0,1,1,0,0,1,0,1,0,0),
      Array(0,1,0,0,1,1,0,0,1,1,1,0,0),
      Array(0,0,0,0,0,0,0,0,0,0,1,0,0),
      Array(0,0,0,0,0,0,0,1,1,1,0,0,0),
      Array(0,0,0,0,0,0,0,1,1,0,0,0,0)
    )

    println(maxAreaOfIsland(area))
  }
}