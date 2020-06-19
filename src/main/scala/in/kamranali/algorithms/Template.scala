package in.kamranali.algorithms

/**
  *
  */

class Template {

  def solve(A: Array[Int]): Int  = 42

}

object TemplateTest extends App {
  val sol = new Template()

  // Test 1
  var A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
  var data = sol.solve(A)
  assert(data == 42)

  // Test 2
  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
  data = sol.solve(A)
  assert(data == 42)

}
