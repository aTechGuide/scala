package guide.atech.algorithms.team.badgeaccess

import scala.collection.SortedSet
import scala.collection.mutable.ListBuffer

object Employee {

  def findSneaky(data: Array[Array[String]]): List[SneakyEmployee] = {

    val mapped = data.foldLeft(Map.empty[String, SortedSet[Int]])((acc, item) => {
      val k = item(0)
      val v = acc.getOrElse(k, SortedSet.empty[Int]) + item(1).toInt

      acc + (k -> v)
    })

    mapped.foldLeft(List.empty[SneakyEmployee])((acc, item) => {
      val (k, v) = item

      val interval = findInterval(v.head, v.tail, List(), false)

      if (interval.isEmpty) acc
      else SneakyEmployee(k, interval) :: acc
    })
  }

  private def parseAsTime(str: Int): Int = {
    val digit = str.toString.length
    val multiplier = 4 - digit

    if (multiplier == 0) str
    else str * math.pow(10, multiplier).toInt
  }

  def findInterval(item: Int, set: SortedSet[Int], ans: List[Int], isIntervalStarted: Boolean): List[Int] = {
    if (set.isEmpty) ans.reverse
    else if (isIntervalStarted) {
      val cur = set.head

      val itemParsed = parseAsTime(item)
      val curParsed = parseAsTime(set.head)

      if (curParsed - itemParsed <= 1000) {
        findInterval(item, set.tail, cur :: ans, isIntervalStarted)
      } else ans.reverse
    }
    else {
      val cur  = set.head

      val itemParsed = parseAsTime(item)
      val curParsed = parseAsTime(cur)

      if (curParsed - itemParsed < 1000) {
        findInterval(item, set, item :: ans , true)
      } else {
        findInterval(cur, set.tail, ans, isIntervalStarted)
      }
    }
  }

}
