package in.kamranali.leetcode.medium

object MinimumPathSum64 {
    // https://www.youtube.com/watch?v=t1shZ8_s6jc
    def minPathSum(grid: Array[Array[Int]]): Int = {
        val rows = grid.length
        val cols = grid(0).length

        val memory = Array.fill[Int](rows, cols)(0)

        (0 until rows).foreach( row => {
            (0 until cols).foreach( col => {

                if (row == 0 && col == 0) memory(row)(col) = grid(row)(col)
                else if (row == 0) memory(row)(col) = grid(row)(col) + memory(row)(col - 1)
                else if (col == 0) memory(row)(col) = grid(row)(col) + memory(row - 1)(col)
                else memory(row)(col) = grid(row)(col) + math.min(memory(row - 1)(col), memory(row)(col - 1))
            })
        })
        memory.foreach(row => println(row.mkString(" ")))
        memory(rows - 1)(cols - 1)
    }
    
    def minPathSumBT(grid: Array[Array[Int]]): Int = {
        
        val rows = grid.length
        val cols = grid(0).length
        
        def minPath(row: Int, col: Int, sum: Int): Int = {
            if (row == rows || col == cols) Int.MaxValue // Invalid Cell
            else if (row == rows - 1 && col == cols - 1) {
                // reached end, return sum
                sum + grid(row)(col)
            }
            else {
                // println(s"Evaluating ($row, $col, $sum)")
                val currElem = grid(row)(col)
                val goRight = minPath(row + 1, col, sum + currElem)
                val goDown = minPath(row, col + 1, sum + currElem)
                
                val min = math.min(goRight, goDown)

                println(s"Min for ($row, $col, $sum) = $min")
                min
            }
        }

        minPath(0, 0, 0)
    }

    def main(args: Array[String]): Unit = {
        val grid = Array(Array(1,3,5),Array(2,1,2),Array(4,3,1))

        println(minPathSum(grid))
    }
}