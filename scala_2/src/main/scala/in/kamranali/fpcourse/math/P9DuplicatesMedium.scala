package in.kamranali.fpcourse.math

object P9DuplicatesMedium {

  // all numbers in the list appear EXACTLY twice, EXCEPT one: find that number

  // optimal
  // ^ = XOR
  // 0 ^ 0 = 0
  // 1 ^ 1 = 0
  // If a is an Int, then a ^ a = 0
  // 0 ^ a = a
  // Complexity: O(N) time, O(1) space
  def duplicates(list: List[Int]): Int = list.reduce(_ ^ _)

  def main(args: Array[String]): Unit = {
    println(duplicates(List(1)))
    println(duplicates(List(1, 2, 1)))
    println(duplicates(List(1, 2, 3, 2, 1)))

    val first1000 = (1 to 100000).toList
    println(duplicates(first1000 ++ List(52369426) ++ first1000))
  }

}
