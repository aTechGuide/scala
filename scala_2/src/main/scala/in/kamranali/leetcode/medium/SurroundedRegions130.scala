package in.kamranali.leetcode.medium

object SurroundedRegions130 {
    def solve(board: Array[Array[Char]]): Unit = {
        val rows = board.length
        val cols = board(0).length
        
        def mark(r: Int, c: Int): Unit = {
            if (r < 0 || r == rows || c < 0 || c == cols) ()
            else if (board(r)(c) == 'X' || board(r)(c) == '#') ()
            else {
                
                board(r)(c) = '#'
                
                mark(r - 1, c) // left
                mark(r + 1, c) // right
                mark(r, c - 1) // Up
                mark(r, c + 1) // Down 
            }
        }
        
        // Adjust first and last row
        (0 until cols).foreach { col => {
            
            if (board(0)(col) == 'O') mark(0, col)
            if (board(rows - 1)(col) == 'O') mark(rows - 1, col)
        }}
        
        // Adjust first and last col
        (0 until rows).foreach { row => {
            
            if (board(row)(0) == 'O') mark(row, 0)
            if (board(row)(cols - 1) == 'O') mark(row, cols - 1)
        }}
        
        // Flip the remaining O to X
        (0 until rows).foreach {row => {
            (0 until cols).foreach {col => {
                if (board(row)(col) == 'O') board(row)(col) = 'X'
            }}
        }}
        
        // Flip the # to O
        (0 until rows).foreach {row => {
            (0 until cols).foreach {col => {
                if (board(row)(col) == '#') board(row)(col) = 'O'
            }}
        }}
        
    }

  def main(args: Array[String]): Unit = {
    val data = Array(Array("X","X","X","X"),Array("X","O","O","X"),Array("X","X","O","X"),Array("X","O","X","X"))
    val dataChar = Array.fill[Char](data.length, data(0).length)('z')

    data.indices.foreach { row => {
      data(0).indices.foreach { col => {
        dataChar(row)(col) = data(row)(col).charAt(0)
      }}
    }}

    solve(dataChar)

  }
}