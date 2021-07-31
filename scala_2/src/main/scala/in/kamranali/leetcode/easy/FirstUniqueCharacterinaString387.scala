package in.kamranali.leetcode.easy

object FirstUniqueCharacterinaString387 {

  def firstUniqChar(s: String): Int = {

    // Map [Char, (Count, Idx)]
    def memory(itr: Int, res: Map[Char, (Int, Int)]): Map[Char, (Int, Int)] = {
      if (itr == s.length) res
      else {
        val elem = s.charAt(itr)
        val (count, _) = res.getOrElse(elem, (0, 0))

        memory(itr + 1, res + (elem -> (count + 1, itr)))
      }
    }

    val parsed = memory(0, Map())

    parsed.foldLeft(Int.MaxValue) {
      case (accum, (_, (count, idx))) =>
        if (count == 1) math.min(idx, accum)
        else accum
    }


  }

}
