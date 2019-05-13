package in.kamranali.patternMatching

object PatternsEverywhere extends App {

  // Catches are actually MATCHES

  try {
    // code
  } catch {
    case e: RuntimeException => "runtime"
    case npe: NullPointerException => "npe"
    case _ => "something else"
  }

  // Generators are also Pattern Matching
  val list = List(1,2,3,4)
  val even = for {
    x <- list if x % 2 == 0
  } yield 10 * x

  val tuples = List((1,2), (3,4))
  val filterTuples = for {
    (first, second) <- tuples
  } yield first * second

  // case classes, :: operators, ...

  // Multiple Value definitions based on Pattern Matching
  val tuple = (1,2,3)
  val (a, b, c) = tuple
  println(b)

  val head :: tail = list
  println(head)
  println(tail)


  /*
  Partial Function: Based on Pattern Matching
   */

  val mappedList1 = list.map { x => x match {
      case v if v % 2 == 0 => v + "is even"
      case 1 => "the one"
      case _ => "something else"
    }
  }

  // Partial Function Literal
  val mappedList2 = list.map {
    case v if v %2 == 0 => v + " is even"
    case 1 => "the one"
    case _ => "something else"
  }

  println(mappedList1)


}
