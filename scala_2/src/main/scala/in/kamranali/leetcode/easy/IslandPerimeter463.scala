package in.kamranali.leetcode.easy

object IslandPerimeter463 {
    def islandPerimeter1(grid: Array[Array[Int]]): Int = {

        val rows = grid.length
        val cols = grid(0).length

        def count(row: Int, col: Int, sum: Int): Int = {

            if (row == rows) sum
            else if (col == cols) count(row + 1, 0, sum)
            else {
                val elem = grid(row)(col)

                if (elem == 1) {

                    val left = if (col == 0) 1 else if (grid(row)(col-1) == 0) 1 else 0
                    val right = if (col + 1 == cols) 1 else if (grid(row)(col+1) == 0) 1 else 0

                    val top = if (row == 0) 1 else if (grid(row - 1)(col) == 0) 1 else 0
                    val bottom = if (row + 1 == rows) 1 else if (grid(row + 1)(col) == 0) 1 else 0

                    val newSum = left + right + top + bottom

                    count(row, col + 1, newSum + sum)
                } else {
                    count(row, col + 1, sum)
                }
            }
        }
        count(0, 0, 0)
    }

    def islandPerimeter(grid: Array[Array[Int]]): Int = {

        val rows = grid.length
        val cols = grid(0).length

        val ans = for {
            row <- 0 until rows
            col <- 0 until cols
        } yield {
            val elem = grid(row)(col)

            if (elem == 1) {

                val left = if (col == 0) 1 else if (grid(row)(col-1) == 0) 1 else 0
                val right = if (col + 1 == cols) 1 else if (grid(row)(col+1) == 0) 1 else 0

                val top = if (row == 0) 1 else if (grid(row - 1)(col) == 0) 1 else 0
                val bottom = if (row + 1 == rows) 1 else if (grid(row + 1)(col) == 0) 1 else 0

                val newSum = left + right + top + bottom

                newSum
            } else {
                0
            }
        }

        ans.sum
    }

    def main(args: Array[String]): Unit = {

        println(islandPerimeter(Array(
            Array(0,1,0,0),
            Array(1,1,1,0),
            Array(0,1,0,0),
            Array(1,1,0,0)
        )))
    }
}