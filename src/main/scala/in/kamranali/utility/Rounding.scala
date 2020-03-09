package in.kamranali.utility

/**
 *  https://stackoverflow.com/questions/11106886/scala-doubles-and-precision
 */
object Rounding {

  val value1 = 90.0
  val value2 = 100.0

  def roundAt(p: Int)(n: Double): Double = { val s = math pow (10, p); (math round n * s) / s }
  def roundAt2(n: Double): Double = roundAt(2)(n)

  val ratio = roundAt2(value1 / value2)

  println(ratio)
}
