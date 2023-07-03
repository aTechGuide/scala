package guide.atech.algorithms.team.badgeaccess

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map

object Employee {

  def findSneaky(data: Array[Array[String]]): List[SneakyEmployee] = {

    val mapped = Map.empty[String, ListBuffer[LocalTime]]
    data.foreach { record => {
      val v = mapped.getOrElseUpdate(record(0), ListBuffer.empty[LocalTime])
      v.addOne(convertDate(record(1)))
    }}

    // println(mapped)

    mapped.foldLeft(List.empty[SneakyEmployee])((acc, item) => {
      val (k, v) = item
      val vSorted = v.sorted

      val interval = findInterval(vSorted.head, vSorted.tail)

      if (interval.isEmpty) acc
      else SneakyEmployee(k, interval) :: acc
    })
  }
  @tailrec
  private def findInterval(prev: LocalTime, list: ListBuffer[LocalTime]): List[LocalTime] = {
    if (list.isEmpty) List()
    else {
      val cur  = list.head
      val diff = ChronoUnit.MINUTES.between(prev, cur)

      if (diff <= 60) {
        val ans = extendInterval(prev, list, List(prev))
        if (ans.nonEmpty) ans
        else findInterval(cur, list.tail)
      } else {
        findInterval(cur, list.tail)
      }
    }
  }

  @tailrec
  private def extendInterval(first: LocalTime, list: ListBuffer[LocalTime], ans: List[LocalTime]): List[LocalTime] = {
    if (list.isEmpty) {
      val ansLen = ans.length
      if (ansLen >= 3) ans.reverse
      else List()
    } else {
      val cur = list.head

      val diff = ChronoUnit.MINUTES.between(first, cur)

      if (diff <= 60) {
        extendInterval(first, list.tail, cur :: ans)
      } else {
        val ansLen = ans.length
        if (ansLen >= 3) ans.reverse
        else List()
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
