package guide.atech.algorithms.team.election

import scala.annotation.tailrec

object Voting {
  def findWinner(votes: Array[String]): String = {
    import scala.collection.mutable

    val voteCount = mutable.Map.empty[String, Int]

    @tailrec
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
