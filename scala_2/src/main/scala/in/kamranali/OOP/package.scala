package in.kamranali

/**
  * Basic Scala Lesson 23 b [Packaging And Imports]
  *
  * Ref
  * - https://www.udemy.com/course/rock-the-jvm-scala-for-beginners/learn/lecture/7660676
  */

package object OOP {

  /*
  Package Object
  - It can only be one per package
  - Name is same as the package that it resides in
  - We can define methods Or constants and use them by there simple name through out the package
   */

  def sayHello: Unit = println("HEllo, Scala")
  val SPEED_OF_LIGHT = 299792458

}
