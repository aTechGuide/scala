package in.kamranali.leetcode.medium

object LargestNumber179 {
    def largestNumber(nums: Array[Int]): String = {

        val ordering: Ordering[String] = Ordering.fromLessThan[String]((a, b) => (a + b).compareTo(b + a)> 0)
        nums.map(_.toString).sorted(ordering).reduce((x, y) => if (x.equals("0")) y else x + y )
    }

    def main(args: Array[String]): Unit = {
        println(largestNumber(Array(10, 2)))
    }
}