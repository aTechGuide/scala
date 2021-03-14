package in.kamranali.OOP

import scala.language.postfixOps

object MethodNotations extends App {

  class Person(val name: String, favouriteMovie: String) {

    def likes(movie: String): Boolean = movie == favouriteMovie

    def hangOutWith(person: Person): String = s"${this.name} is hanging out with ${person.name}"

    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}" // Same as `hangOutWith`

    def unary_! : String = s"You used ! (bang)"

    def isAlive: Boolean = true

    def apply(): String = s"Hi, my name is $name and I called Apply"// ` () ` are important

  }

  val sam = new Person("sam", "Inception")

  println(sam.likes("Inception"))

  /*
  INFIX Notation / Operator Notation (Syntactic Sugar)

  1. We can use this syntax with method accepting one parameter
   */
  println(sam likes "Inception") // This is equivalent to println(sam.likes("Inception"))

  /*
  Operators in Scala
   */
  val harry = new Person("harry", "Fight Club")
  println(sam hangOutWith harry)
  // ` hangOutWith ` is kind of behaving as an operator between sam and harry
  // In scala we can even rename ` hangOutWith ` as ` + `

  println(sam + harry)

  // Even `+` operators in Numbers are methods as well

  println(1 + 2)

  println(1.+(2))

  // ALL OPERATORS ARE METHODS.

  /*
  Prefix Notation
  1. Its all about Unary operators
   */

  val x = -1 // `-` is a unary operator

  val y = 1.unary_- // equivalent to ` val x = -1 `

  // unary_prefix only works with - + ~ !

  println(!sam)
  println(sam.unary_!)

  /*
  Postfix Notation
  1. Only available to Methods without Parameters
   */
  println(sam.isAlive)
  println(sam isAlive)

  /*
  Apply
   */
  println(sam.apply())
  println(sam()) // equivalent ` println(sam.apply()) `

}
