package in.kamranali.implicits

/**
  * Advance Scala Lesson 33 [Enter Implicits]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/11053814
  */

object ImplicitsIntro extends App {

  // '->' is an implicit method
  val pair: (String, String) = "Daniel" -> "555"
  // "Daniel" is converted into an instance of `ArrowAssoc` and
  // then it will call the `->` method on it with the "555"  argument and will return a tuple.


  val intPair = 1 -> 2

  case class Person(name: String) {
    def greet = s"Hi , my name is $name"
  }

  implicit def fromStringToPerson(str: String): Person = Person(str)

  /*
    We don't have `greet` method in String class.
    But before giving up, compiler looks into all implicit classes, objects, values and methods.
    That can help in compilation.

    If more than 1 implicits matches for the conversion, Compiler will complain
   */
  println("Peter".greet) // compiler conversion -> println(fromStringToPerson("Peter").greet)

  /**
    Implicit Parameters
   */
  def increment(x: Int)(implicit amount: Int): Int = x + amount
  implicit val defaultAmount: Int = 10

  println(increment(2)) // 'defaultAmount' will be implicitly passed by compiler as second parameter list
  // CAVEAT: Its NOT default args because implicit arguments are bind by the compiler from its search scope



}
