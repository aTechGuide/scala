package in.kamranali.hackerrank.medium

object SherlockAndAnagrams {

  def sherlockAndAnagrams(s: String): Int = {
    def calculateFreqMap(i: Int, j: Int, fm: Map[String, Int]): Map[String, Int] = {
      if ( i == s.length) fm
      else if (j == s.length) calculateFreqMap(i + 1, i + 1, fm)
      else {
        val ss = s.substring(i, j + 1)
        println(ss)
        val k = ss.sorted

        val count = fm.getOrElse(k, 0) + 1
        val nFm = fm + (k -> count)

        calculateFreqMap(i, j + 1, nFm)
      }
    }

    // driver
    val fm = calculateFreqMap(0, 0, Map())
    fm.keys.foldLeft[Int](0)((accum, k) => {
      val v = fm(k)
      val ans = (v * (v - 1)) / 2

      accum + ans
    })
  }

  def main(args: Array[String]): Unit = {
    println(sherlockAndAnagrams("abba"))
  }

}
