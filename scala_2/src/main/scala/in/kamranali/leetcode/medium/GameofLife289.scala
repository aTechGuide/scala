package in.kamranali.leetcode.medium

object GameofLife289 {
    def gameOfLife(board: Array[Array[Int]]): Unit = {
        
        val rows = board.length
        val cols = board(0).length
        
        val copied = Array.fill[Int](rows, cols)(0)

        for {
            row <- 0 until rows
            col <- 0 until cols
        } {
            copied(row)(col) = board(row)(col)
        }
        
        val dirs = Array(Array(0,-1),Array(0,1),Array(1,0),Array(1,-1),Array(1,1),Array(-1,-1),Array(-1,1),Array(-1,0))
        
        def calculate(curr: Int, lCount: Int): Int = {
            if (curr == 1) {
                if (lCount < 2) 0 // under-population
                else if (lCount ==2 || lCount == 3) 1 // lives
                else 0 // else if (lCount > 3) 0 // Over population 
            } else {
                if (lCount == 3) 1
                else 0
            }
        }
        
        def create(row: Int, col: Int): Unit = {
            
            if (row == rows) ()
            else if (col == cols) create(row + 1, 0)
            else {
             
                val live = dirs.foldLeft(0)((acc, value) => {
                    val nRow = row + value(0)
                    val nCol = col + value(1)
                    
                    if (nRow >=0 && nCol >= 0 && nRow < rows && nCol < cols) {
                        acc + copied(nRow)(nCol)
                    } else acc
                })
                
                board(row)(col) = calculate(copied(row)(col), live)

                create(row, col + 1)
            }
        }
        
        create(0, 0)
        
    }

    def main(args: Array[String]): Unit = {
        val inp = Array(
            Array(0,1,0),
            Array(0,0,1),
            Array(1,1,1),
            Array(0,0,0)
        )

        gameOfLife(inp)

        inp.foreach(row => println(row.mkString(" ")))
    }
}