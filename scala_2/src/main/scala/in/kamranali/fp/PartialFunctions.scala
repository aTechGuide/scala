package in.kamranali.fp

/**
  * Advance Scala Lesson 9 [Partial Functions]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/10937350
  */

object PartialFunctions extends App {

  val aFunction = (x: Int) => x + 1 // Function1[Int,Int] === Int => Int

  // Above functions all Integers of Int Domain.

  // If we want to restrict the input of `aFunction` to specific range

  // Method 1
  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException

  // Method 2
  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
  }

  // Method 3 (Partial Functions)
  // Partial Functions are based on pattern Matching
  val aPartialFUnction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
  } // Portion between curly braces is Partial Function Value

  println(aPartialFUnction(2))

  // println(aPartialFUnction(6)) // Crash

  /**
    Utilities of Partial Function
   */

  // 1. Test whether a partial function is applicable for this argument
  println(aPartialFUnction.isDefinedAt(67)) // false

  // 2. Lifted to Total Function returning options
   val lifted: Int => Option[Int] = aPartialFUnction.lift // Int => Option[Int]

  println(lifted(2)) // Some(56)
  println(lifted(98))  // None

  // 3. Chaining
  val pfChain = aPartialFUnction.orElse[Int, Int] {
    case 45 => 67
  }

  println(pfChain(2)) // 56
  println(pfChain(45)) // 67

  // 4. PF extend Normal Function
  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  // In above total function `aTotalFunction: Int => Int` we supplied a Partial Function Literal i.e. {...}
  // Because partial functions are sub types of Total Functions

  // Which as a Side effect we imply

  // HOFs accept partial functions as well

  val aMappedList = List(1,2,3).map {
    case 1 => 42
    case 2 => 78
    case 3 => 1000
  }

  println(aMappedList) // List(42, 78, 1000)

  // But if we have an entry in List which is not contained in Partial Function; Code will crash

  /**
    Note: Partial Functions can only have ONE Parameter type
   */

}
