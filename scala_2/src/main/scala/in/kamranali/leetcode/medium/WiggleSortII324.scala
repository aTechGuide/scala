package in.kamranali.leetcode.medium

object WiggleSortII324 {
  def wiggleSort(nums: Array[Int]): Unit = {

    val sorted = nums.sorted
    val len = sorted.length

    def wiggle(l: Int, r: Int, idx: Int): Unit = {
      if (l < 0) ()
      else {
        nums(idx) =  sorted(l)
        if ((idx + 1) < len)nums(idx + 1) = sorted(r)

        wiggle(l - 1, r - 1, idx + 2)

      }
    }

    val mid = len / 2
    val left = if (len % 2 == 1) mid else mid - 1
    wiggle(left, len - 1, 0)

  }

  def main(args: Array[String]): Unit = {
    val data = Array(1,5,1,1,6,4)
    wiggleSort(data)

    println(data.mkString(" "))
  }
}