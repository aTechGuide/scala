package guide.atech.algorithms.election

import scala.collection.immutable.TreeMap

object Voting {
  def findWinner(votes: Array[String]): String = {
    import scala.collection.mutable.Map

    val voteCount = Map.empty[String, Int]

    def util(idx: Int, ans: String, ansCount: Int): String = {
      if (idx == votes.length) ans
      else {
        val vote = votes(idx)
        val count = voteCount.getOrElse(vote, 0)
        val newCount = count + 1

        voteCount.put(vote, newCount)

        if (newCount > ansCount) util(idx + 1, vote, ansCount + 1)
        else util(idx + 1, ans, ansCount)
      }
    }

    util(0, "", 0)
  }
}
