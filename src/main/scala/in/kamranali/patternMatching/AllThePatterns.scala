package in.kamranali.patternMatching

import in.kamranali.collections.custom.{Cons, Empty, MyList}

object AllThePatterns extends App {

  // Constants

  val x: Any = "Scala"
  val constants = x match {
    case 1 => "a number"
    case "Scala" => "The Scala"
    case true => "The Truth"
    case AllThePatterns => "A Singleton object"
  }

  // Match anything
  // Wildcard

  val matchAnything1 = x match {

    case _ =>
  }

  // variable
  val matchAVariable = x match {

    case something => s"I've found $something"
  }

  // Tuples
  val aTuple = (1,2)
  val matchATuple = aTuple match {
    case (1,1) =>
    case (something, 2) => s"I've found $something" // Nested patterns, ` something ` is extracted from Tuple if rest of the pattern matches
  }

  println(matchATuple)

  val nestedTuples = (1, (2,3))
  val matchNestedTuple = nestedTuples match {
    case (_, (2, v)) => s"I've found" // Pattern matching can be nested
  }

  // case classes (constructor patter)
  val aList: MyList[Int] = Cons(1, Cons(2, Empty ))
  val matchAList = aList match {

    case Empty =>
    case Cons(head, tail) => // Pattern Matching can be lested with Case Class as well
  }

  // List patterns
  val aStandardList = List(1,2,3,42)
  val standardMatching = aStandardList match {
    // Even though List isn't case class its extractor exists
    case List(1, _, _, _) => // extractor for list which tries to match the list with 4 things in which 1 is first element
    case List(1, _*) => // List of arbitrary length
    case 1 :: List(_) => // infix pattern
    case List(1,2,3) :+ 42 => // infix pattern
  }

  // Type Specifiers
  val unknown: Any = 2
  val unknownMatch = unknown match {
    case list: List[Int] =>  // explicit type specifier
    case _ =>
  }

  // Name binding
  val nameBindingMatch = aList match {
    case nonEmptyList @ Cons(_, _) => // name binding; which allows us to use the name later (here)
    case Cons(1, rest @ Cons(2, _)) => // name binding inside nested patters
  }

  // Multi patterns
  val multipattern = aList match {
    case Empty | Cons(0, _) => // compund pattern (multi-pattern)
  }

  // if guards
  val secondElementSpecial = aList match {
    case Cons(_, Cons(specialElement, _)) if specialElement % 2 == 0 => // ` if specialElement % 2 ` is predicate
  }

  /*
  Exercise
   */

  val numbers = List(1,2,3)
  val numbersMatch = numbers match {
    case listOfStrings: List[String] => "a list of strings"
    case listOfNumbers: List[Int] => "a list of Numbers"
    case _ => ""
  }

  println(numbersMatch) // a list of strings
  // Because after Type checking all the Generic Types are removed. Which makes JVM absolutely oblivious to generic types. So after erasure, List[String] => List
}
