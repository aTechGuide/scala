package in.kamranali.algorithms.backtracking

/**
  * https://www.youtube.com/watch?v=a-sMgZ7HGW0
  */

class AllMnemonicsForPhoneNumber {

  val dialpad: Map[Int, String] = Map(1 -> "", 2 -> "ABC", 3 -> "DEF", 4 -> "GHI",5 -> "JKL",6 -> "MNO",7 -> "PQES",8 -> "TUV",9 -> "WXYZ")

  case class Result(var value: List[String])
  case class Holder(var value: Array[String])

  def solve(A: Int): List[String]  = {

    val len = A.toString.length

    val res = Result(List.empty[String])
    val holder = Holder(Array.fill[String](len)(""))


    def solveUtil(input: String, len: Int, idx: Int, result: Result, holder: Holder): Unit = {

      if (idx == len) result.value = result.value :+ holder.value.mkString("")
      else {
        dialpad(input(idx).toString.toInt).foreach { charVal =>

          holder.value(idx) = charVal.toString // Make a Choice
          solveUtil(input, len, idx + 1, result, holder) // Recurse with that selection

          holder.value(idx) = "" // Undo that Choice
        }
      }
    }

    solveUtil(A.toString, len, 0, res, holder)
    res.value
  }

}

object AllMnemonicsForPhoneNumberTest extends App {
  val sol = new AllMnemonicsForPhoneNumber()

  // Test 1
  var A = 23
  var data = sol.solve(A)
  println(data)
//  assert(data == 42)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.solve(A)
//  assert(data == 42)

}
