package guide.atech.algorithms.team.badgeaccess

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import scala.annotation.tailrec
import scala.collection.SortedSet
import scala.collection.mutable.ListBuffer

object Employee {

  def findSneaky(data: Array[Array[String]]): List[SneakyEmployee] = {

    val mapped = data.foldLeft(Map.empty[String, SortedSet[LocalTime]])((acc, item) => {
      val k = item(0)
      val v = acc.getOrElse(k, SortedSet.empty[LocalTime]) + convertDate(item(1))

      acc + (k -> v)
    })

    //println(mapped)

    mapped.foldLeft(List.empty[SneakyEmployee])((acc, item) => {
      val (k, v) = item

      val interval = findInterval(v.head, v.tail, List(), false)

      if (interval.isEmpty) acc
      else SneakyEmployee(k, interval) :: acc
    })
  }
  @tailrec
  private def findInterval(item: LocalTime, set: SortedSet[LocalTime], ans: List[LocalTime], isIntervalStarted: Boolean): List[LocalTime] = {
    if (set.isEmpty) ans.reverse
    else if (isIntervalStarted) {
      val cur = set.head

      val diff = ChronoUnit.MINUTES.between(item, cur)

      if (diff <= 60) {
        findInterval(item, set.tail, cur :: ans, isIntervalStarted)
      } else ans.reverse
    }
    else {
      val cur  = set.head
      val diff = ChronoUnit.MINUTES.between(item, cur)

      if (diff <= 60) {
        findInterval(item, set, item :: ans , true)
      } else {
        findInterval(cur, set.tail, ans, isIntervalStarted)
      }
    }
  }

  private def convertDate(strDate: String): LocalTime = {
    val dtf = DateTimeFormatter.ofPattern("HHmm")
    val dateLen = strDate.length

    val correctString =
      if (dateLen == 4) strDate
      else if (dateLen == 3) "0" + strDate
      else if (dateLen == 2) strDate + "00"
      else "0" + strDate + "00"

    LocalTime.parse(correctString, dtf)
  }

}
