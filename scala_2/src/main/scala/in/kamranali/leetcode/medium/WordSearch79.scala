package in.kamranali.leetcode.medium

object WordSearch79 {
    
    // Ref: https://leetcode.com/problems/word-search/discuss/27658/Accepted-very-short-Java-solution.-No-additional-space./26671
    def exist(board: Array[Array[Char]], word: String): Boolean = {
        
        val rows = board.length
        val cols = board(0).length
        
        def exist(i: Int, j: Int, idx: Int): Boolean = {
            if (idx == word.length) true // we found a match
            else if (i < 0 || i == rows || j < 0 || j == cols) false // invalid coordinates
            else if (board(i)(j) == '#' || board(i)(j) != word.charAt(idx)) false // Not a good choice as either cell already taken Or No match found
            else {
                // Scenario where match is found for current character
                val elem = board(i)(j)
                
                // Make a choice
                board(i)(j) = '#'

                // search for solution in all directions, short circuit if a solution is found
                val res = exist(i + 1, j, idx + 1) || exist(i - 1, j, idx + 1) || exist(i, j - 1, idx + 1) || exist(i, j + 1, idx + 1)
                
                if (res) true
                else {
                    // Remove the choice
                    board(i)(j) = elem
                    false
                }
            }
        }

        // Check all the cells for a probable solution
        def check(row: Int, col: Int): Boolean = {
            if (row == rows) false
            else if (col == cols) check(row + 1, 0)
            else {
                // return if a solution is found at this coordinate
                if (exist(row, col, 0)) true
                else check(row, col + 1)
            }
        }
        check(0, 0)
    }
}