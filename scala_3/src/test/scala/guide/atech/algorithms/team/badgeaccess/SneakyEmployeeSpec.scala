package guide.atech.algorithms.team.badgeaccess

import guide.atech.algorithms.team.badgeaccess.Employee

import scala.collection.SortedSet

class SneakyEmployeeSpec extends munit.FunSuite {

  test("Find Sneaky Employee") {

    val data = Array(
      Array("Paul", "1355"), Array("Jennifer", "1910"), Array("Paul", "1315"),
      Array("Chloe", "1910"), Array("Jose", "1615"), Array("Jose", "1640"), Array("Paul", "1416"),
      Array("Paul", "1405"), Array("Jennifer", "1335"), Array("Jose", "1630"), Array("Chloe", "1909"),

      // first digit hour and rest minutes, add 0 front
      Array("Jose", "835"), Array("Jose", "830"), Array("Jose", "855"), Array("Jose", "930"),
      Array("Jose", "915"), Array("Jose", "730"), Array("Jose", "940"), Array("Jennifer", "730"),
      Array("Zhang", "109"), Array("Zhang", "110"), Array("Amos", "500"), Array("Amos", "503"),
      Array("Amos", "601"), Array("Amos", "602"), Array("Amos", "400"), Array("Amos", "504"),

      // only hour, add 00 back
      Array("Zhang", "10"),

      // only hour, add 0 front and 00 back
      Array("Chloe", "0"), Array("Jennifer", "5"), Array("Zhang", "1"),
      Array("Amos", "1"), Array("Amos", "2"),
    )
    /*
      Expected output (in any order)
      Amos: 500 503 504
      Paul: 1315 1355 1405. // 1315-1415
      Zhang: 10 109 110
      Jose: 830 835 855 915 930

      Correct = Amos
    */
    //println(data.map(d => Employee.convertDate(d(1))).toList.sorted)
    val ans = Employee.findSneaky(data)
    ans.foreach { item => {
      println(s"$item")
    }}
  }

}
