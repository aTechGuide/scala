package guide.atech.algorithms.cflt.patternmatching

import scala.annotation.tailrec

class BasicPatternMatching extends PatternMatching[String, Boolean] {

  override def isMatch(s: String, p: String): Boolean = {

    val sLen = s.length
    val pLen = p.length

    @tailrec
    def util(sIdx: Int, pIdx: Int, btSPtr: Int, btPPtr: Int): Boolean = {
      if (sIdx == sLen) (pIdx until p.length).forall(idx => p.charAt(idx) == '*')
      else if (pIdx < pLen && (s.charAt(sIdx) == p.charAt(pIdx) || p.charAt(pIdx) == '?')) util(sIdx + 1, pIdx + 1, btSPtr, btPPtr)
      else if (pIdx < pLen && p.charAt(pIdx) == '*') util(sIdx, pIdx + 1, sIdx + 1, pIdx) // Ignoring * but caching the position
      else if (btPPtr != -1) util(btSPtr, btPPtr + 1, btSPtr + 1, btPPtr) // if BT possible, start matching from next char to * and next match
      else false // no match and wildcard
    }

    // Driver
    util(0, 0, 0, -1)
  }
}
