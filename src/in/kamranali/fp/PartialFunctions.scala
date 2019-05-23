package in.kamranali.fp

object PartialFunctions extends App {

  val aFunction = (x: Int) => x + 1 // Function[Int,Int] === Int => Int

  // Above functions all Integers of Int Domain.

  // If we want to restrict the input of `aFunction` to specific range

  // Method 1
  val aFusyFunction = (x: Int) =>
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

  /*
  Utilities of Partial Function
   */

  // 1. Test whether a partial function is applicable for this argument
  println(aPartialFUnction.isDefinedAt(67))

  // 2. Lifted to Total Function returning options
   val lifted = aPartialFUnction.lift // Int => Option[Int]

  println(lifted(2))
  println(lifted(98))

  // 3. Chaining
  val pfChain = aPartialFUnction.orElse[Int, Int] {
    case 45 => 67
  }

  println(pfChain(2))
  println(pfChain(45))

  // 4. extend Normal Function
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

  println(aMappedList)

  // But if we have an entry in List which is not contained in Partial Function; Code will crash

  /*
    Note: Partial Functions can only have ONE Parameter type
   */

  // Exercise

}
