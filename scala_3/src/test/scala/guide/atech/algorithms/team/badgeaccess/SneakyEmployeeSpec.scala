package guide.atech.algorithms.badgeaccess

import guide.atech.algorithms.team.badgeaccess.Employee

import scala.collection.SortedSet

class SneakyEmployeeSpec extends munit.FunSuite {

  test("") {

    // We don't know what 1 means; is it 1 hour or minute
    val data = Array(
      Array("Paul", "1355"), Array("Jennifer", "1910"), Array("Jose", "835"),
      Array("Jose", "830"), Array("Paul", "1315"), Array("Chloe", "0"),
      Array("Chloe", "1910"), Array("Jose", "1615"), Array("Jose", "1640"),
      Array("Paul", "1405"), Array("Jose", "855"), Array("Jose", "930"),
      Array("Jose", "915"), Array("Jose", "730"), Array("Jose", "940"),
      Array("Jennifer", "1335"), Array("Jennifer", "730"), Array("Jose", "1630"),
      Array("Jennifer", "5"), Array("Chloe", "1909"), Array("Zhang", "1"),
      Array("Zhang", "10"), Array("Zhang", "109"), Array("Zhang", "110"),
      Array("Amos", "1"), Array("Amos", "2"), Array("Amos", "400"),
      Array("Amos", "500"), Array("Amos", "503"), Array("Amos", "504"),
      Array("Amos", "601"), Array("Amos", "602"), Array("Paul", "1416"),
    )
    val ans = Employee.findSneaky(data)
    println(ans)
  }

  test("Sorted Set picks correct interval") {

    val data = SortedSet(730, 830, 835, 855, 915, 930, 940, 1615, 1630, 1640)
    val ans = Employee.findInterval(data.head, data.tail, List(), false)

    //println(ans)
    assertEquals(ans, List(830, 835, 855, 915, 930))
  }

}
