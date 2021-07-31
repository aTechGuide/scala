package in.kamranali.leetcode.medium

object RotateImage48 {
  def rotate(matrix: Array[Array[Int]]): Unit = {

    val rows = matrix.length
    val cols = matrix(0).length

    def r(row: Int, col: Int, itr: Int): Unit = {

      if (row == (rows / 2)) ()
      else if ( itr == cols - 1 - col - row) r(row + 1, col + 1, 0)
      else {

        val e1 = matrix(row)(col + itr)
        val e2 = matrix(row + itr)(cols - 1 - col)
        val e3 = matrix(rows - 1 - row)(cols - 1 - col - itr)
        val e4 = matrix(rows - 1 - row - itr)(col)

        matrix(row)(col + itr) = e4
        matrix(rows - 1 - row - itr)(col) = e3
        matrix(rows - 1 - row)(cols - 1 - col - itr) = e2
        matrix(row + itr)(cols - 1 - col) = e1

        r(row, col, itr + 1)
      }
    }

    r(0, 0, 0)
  }

  def main(args: Array[String]): Unit = {
    val odd = Array(
      Array(1,2,3,4,5),
      Array(6,7,8,9,10),
      Array(11,12,13,14,15),
      Array(16,17,18,19,20),
      Array(21,22,23,24,25)
    )

    val even = Array(
      Array(5,1,9,11),
      Array(2,4,8,10),
      Array(13,3,6,7),
      Array(15,14,12,16)
    )

    rotate(odd)
//    rotate(even)

    odd.foreach( row => println(row.mkString(" ")))
    //even.foreach( row => println(row.mkString(" ")))
  }
}