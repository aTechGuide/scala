package in.kamranali.fp

import scala.annotation.tailrec

object HigherOrderFuncAndCurry extends App {

  /*
  Higher Order Function:
    Functions that either
    - Takes functions as parameters
    - Returns functions as results

    e.g. map, flatmap, filter
   */

  // Applying function f, n Times on value x
  @tailrec
  def nTimes(f: Int => Int, n: Int, x: Int): Int = {
    if (n <= 0) x
    else nTimes(f, n - 1, f(x))
  }

  val plusOne = (x: Int) => x + 1

  println(nTimes(plusOne, 10, 1))

  /*
  Example 2
   */
  // Returning lambda which will apply function f, n times on value supplied to it
  def nTimesBetter(f: Int => Int, n: Int): (Int => Int) = {
    if(n <= 0) (x: Int) => x
    else (x: Int) => nTimesBetter(f, n-1)(f(x))
  }

  val plusTen = nTimesBetter(plusOne, 10)
  println(plusTen(1))


  /*
  Curried Function
  - Helps in defining Helper functions that we want to use on various values
  - '=>' is right Associative
   */
  val superAdder = (x: Int) => (y: Int) => x + y
  val add3 = superAdder(3) // add3 is a Lambda of form `y => y + 3`

  println(add3(2))
  println(superAdder(3)(10))

  /*
  Function with multiple parameter list
  This function will act like curried Functions
   */

  def curriedFormatter(c: String)(x: Double): String = c.format(x)

  // Let's create Sub-Functions from curriedFormatter
  // 'standardFormat' is a function from Double to String
  val standardFormat: (Double => String) = curriedFormatter("%4.2f")
  val preciseFormat: (Double => String) = curriedFormatter("%10.8f")

  // PS: We have to specify type of `standardFormat` and `preciseFormat` else the code will not compile

  println("Standarf Format: " + standardFormat(Math.PI))
  println("Precise Format: " + preciseFormat(Math.PI))
}
