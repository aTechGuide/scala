package in.kamranali.implicits

object ImplicitsIntro extends App {

  // '->' is an implicit method
  val pair = "Daniel" -> "555"
  val intPair = 1 -> 2

  case class Person(name: String) {
    def greet = s"Hi , my name is $name"
  }

  implicit def fromStringToPerson(str: String): Person = Person(str)

  /*
    We don't have `greet` method in String class. But before giving up, compiler looks into all implicit classes, objects, values and methods.
    That can help in compilation.

    if more than 1 implicits matches for the conversion, Compiler will complain
   */
  println("Peter".greet) // compiler conversion -> println(fromStringToPerson("Peter").greet)

  /*
    Implicit Parameters
   */
  def increment(x: Int)( implicit amount: Int) = x + amount
  implicit val defaultAmount = 10

  println(increment(2)) // 'defaultAmount' will be implicitly passed by compiler as second parameter list
  // CAVEAT: Its not default args because implicit arguments are bind by the compiler from its search scope



}
