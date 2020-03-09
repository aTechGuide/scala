package in.kamranali.basics

import scala.util.Try

object DarkSyntaxSugars extends App {


  // 1. Methods with Single Parameters

  def singleArgumentMethod(arg: Int): String = s"Called with $arg "

  val description = singleArgumentMethod {
    // write some code
    42 // return some result
  }

  println(description)

  val aTryInstance = Try { // Java Try {...}

  }

  // In above syntax, Try methods apply method is called with argument between curly braces

  List(1,2,3).map { x =>
    x + 1

  }

  /*
  2. Single abstract method pattern

  Instances of trait with single method can actually reduce to Lambdas
   */

  trait Action {
    def act(x: Int): Int
  }

  val anInstance: Action = new Action {
    override def act(x: Int): Int = x + 1
  }

  // Reducing anInstance to an Lambda

  val aFunkyInstance: Action = (x: Int) => x + 1

  // examples: Runnable

  val aThread = new Thread( new Runnable {
    override def run(): Unit = println("Hello Scala")
  })

  val aSweeterThread = new Thread(() => println("Sweet Scala"))

  abstract class AnAbstractType {
    def implemented: Int = 23

    def f(a: Int): Unit
  }

  val anAbstractInstance: AnAbstractType = (a: Int) => println("Sweet")

  /*
  3. the :: and #:: methods which are special
   */

  val prependedList = 2 :: List(3,4)

  // What we think is => 2.::(List(3,4))
  // But there is NO :: method in Int

  // Infact the compiler rewrites it as List(3,4).::(2) But how ??

  // It happened because associativity of a method is decided by operators last character
  // If it ends in a colon (:) it means it is right associative else it is left associative


  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this // actual implementation here
  }

  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int] // because -->: is right associative as it ends with colon

  /*
  4. Multi-word Method Naming
   */

  class TeenGirl(name: String) {
    // A method of 3 words
    def `and then said` (gossip: String) = println(s"$name said $gossip")
  }

  val lilly = new TeenGirl("Lilly")
  lilly `and then said` "Scala is so sweet!"

  /*
  5. infix types
   */
  class Composite[A, B]

  // Normal way
  val composite1: Composite[Int, String] = ???

  // Infix way
  val composite2: Int Composite String = ???

  class -->[A, B]
  val towards: Int --> String = ???

  /*
  6. update Method is special much like apply()
   */

  val anArray = Array(1,2,3)
  anArray(2) = 7 // rewritten anArray.update(2, 7)
  // `update` takes 2 params and returns Unit

  // used in mutable collections

  /*
  7. Setters for mutable containers
   */

  class Mutable {
    private var internalMember: Int = 0 // private for Encapsulation

    def member = internalMember // "getter"

    def member_=(value: Int) = internalMember = value // "setter"

  }

  val aMutableContainer = new Mutable
  aMutableContainer.member = 42 // rewritten as aMutableContainer.member_=(42)


}
