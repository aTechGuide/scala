package in.kamranali.leetcode.medium

object RotateImage48 {
  def rotate(matrix: Array[Array[Int]]): Unit = {
    val dim = matrix.length

    def adjust(layer: Int, itr: Int): Unit = {

      if (layer == dim / 2) ()
      else if (itr == dim - layer - 1) adjust(layer + 1, layer + 1)
      else {
        val e1 = matrix(layer)(itr)
        val e2 = matrix(itr)((dim-1) - layer)
        val e3 = matrix((dim-1) - layer)((dim-1) - itr)
        val e4 = matrix((dim-1) - itr)((dim-1) - (dim-1) - layer)

        matrix(layer)(itr) = e4
        matrix((dim-1) - itr)((dim-1) - (dim-1) - layer) = e3
        matrix((dim-1) - layer)((dim-1) - itr) = e2
        matrix(itr)((dim-1) - layer) = e1

        adjust(layer, itr + 1)
      }
    }

    adjust(0, 0)
  }

  def main(args: Array[String]): Unit = {

    val data = Array(
      Array(1,2,3),
      Array(4,5,6),
      Array(7,8,9)
    )

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

    rotate(data)
//    rotate(even)

    data.foreach( row => println(row.mkString(" ")))
    //even.foreach( row => println(row.mkString(" ")))
  }
}