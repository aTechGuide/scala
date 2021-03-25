package in.kamranali.fpcourse.strings

import scala.annotation.tailrec

/*
  Example
  0.9 < 1.0.3.4 < 1.1.0 < 2.0 < 2.1 == 2.01

  -1: ver1 < ver2
   0: ver1 == ver2
   1: ver1 > ver2
 */
object CompareVersionNumbers extends App {

  // Complexity: O(max(l1, l2)); Space -> O(L1 + L2)
  def compareVersion(version1: String, version2: String): Int = {

    @tailrec
    def tailrec(remainVer1: List[Int], remainVer2: List[Int]): Int = {

      if (remainVer1.isEmpty && remainVer2.isEmpty) 0
      else if (remainVer1.isEmpty)
        if (remainVer2.exists( _ != 0)) -1 // Ver2 big
        else 0
      else if (remainVer2.isEmpty)
        if (remainVer1.exists(_ != 0)) 1 // Ver1 big
        else 0
      else if (remainVer1.head > remainVer2.head) 1
      else if (remainVer1.head < remainVer2.head) -1
      else tailrec(remainVer1.tail, remainVer2.tail)
    }

    def preprocess(string: String): List[Int] =
      string.split("\\.").toList.map(_.toInt)

    tailrec(preprocess(version1), preprocess(version2))
  }

  assert(compareVersion("0.9", "1.0.3.4") == -1)
  assert(compareVersion("1.0.3.4", "1.1.0") == -1)
  assert(compareVersion("1.1.0", "2.0") == -1)
  assert(compareVersion("2.0", "2.1") == -1)
  assert(compareVersion("2.1", "2.01") == 0)
  assert(compareVersion("1.0", "1.0.0") == 0)

}

object test extends App {

  println(List(1,2, 3).exists(_ != 1))


}
