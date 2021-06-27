package in.kamranali.fpcourse.graphs

object TownJudge {

  /*
    n people, 1 to n
    trust = List[(a, b)]
    where (a,b) means a trusts b

    There might be a town judge with following properties
    - The town judge trusts nobody. i.e. outDegree(tj) = 0
    - Everybody (except for the town judge) trusts the town judge. == inDegree(tj) = n-1
    - There is exactly one person that satisfies these properties.

    Find the town judge, or return -1.
   */

  def findJudge(n: Int, trust: List[(Int, Int)]): Int = {

    val inDegree = trust.foldLeft[Map[Int, Int]](Map()) {
      case (map, (_, b)) => map + (b -> (map.getOrElse(b, 0) + 1))
    }

    val outDegree = trust.foldLeft[Map[Int, Int]](Map()) {
      case (map, (a, _)) => map + (a -> (map.getOrElse(a, 0) + 1))
    }

    (1 to n)
      .find(elem => inDegree.getOrElse(elem, 0) == (n-1) && outDegree.getOrElse(elem, 0) == 0)
      .getOrElse(-1)

  }

  def main(args: Array[String]): Unit = {
    println(findJudge(2, List((1, 2)))) // 2
    println(findJudge(3, List((1, 2), (3, 2)))) // 2
    println(findJudge(3, List((1, 2), (2, 3), (3, 1)))) // -1
    println(findJudge(4, List((1, 3), (2, 3), (1, 4), (2, 4), (3, 4)))) // 4
  }

}
