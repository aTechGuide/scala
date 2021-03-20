package in.kamranali.algorithms

/**
  *
  */

class Template {

  def solve(A: Array[Int]): Int  = 42

}

object TemplateTest extends App {
  val sol = new Template()
  var A: Array[Int] = _
  var data: Int = _

  // Test 1
  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
  data = sol.solve(A)
  assert(data == 42)

  // Test 2
  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
  data = sol.solve(A)
  assert(data == 42)

}
