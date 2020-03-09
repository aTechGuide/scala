package in.kamranali.patternMatching

object AdvancePatternMatching1 extends App {

  /*
  Structures available for pattern Matching are
  - Constants
  - wildcards
  - case classes
  - tuples
  - some special magic like above
   */

  /*
  Making our Normal Classes compatible to pattern Matching
   */

  // We can't make Person `case` class but still want it to make compatible to pattern matching
  class Person(val name: String, val age: Int)

  // step 1: Define object
  object PersonPattern {

    // step 2: Define unapply with return Types as Type of the result that we want to decompose
    def unapply(person: Person): Option[(String, Int)] = {
      if (person.age < 21) None
      else Some((person.name, person.age))
    }

    // Overloading apply
    def unapply(age: Int): Option[String] = {
      Some(if (age < 21) "minor" else "major")
    }
  }

  val bob = new Person("Bob", 20)

  val greeting = bob match {
    case PersonPattern(n,a) => s"Hi, my name is $n and age is $a"
    case _ => "No Match"
  }
  println(greeting)

  // `bob.age` is first argument of `apply` and `status` is what returns from the `apply`
  val legalStatus = bob.age match {
    case PersonPattern(status) => s"My legal status is $status"
  }
  println(legalStatus)

  /*
  Exercise
   */

  val n: Int = 45
  val mathProperty = n match {
    case x if x < 10 => "single digit"
    case x if x % 2 == 0 => "an even number"
    case _ => "no property"
  }

  // To improve above code
  object even {
    def unapply(arg: Int): Option[Boolean] =
      if (arg % 2 == 0) Some(true)
      else None
  }

  object singleDigit {
    def unapply(arg: Int): Option[Boolean] =
      if (arg > -10 && arg < 10) Some(true)
      else None
  }

  val n1: Int = 1
  val mathProperty1 = n1 match {
    case singleDigit(_) => "single digit"
    case even(_) => "an even number"
    case _ => "no property"
  }
  println(mathProperty1)

  // Further improving Even and Single Digit

  // As we don't need return type we can improve even
  object betterEven {
    def unapply(arg: Int): Boolean = arg % 2 == 0
  }

  // Similarly we can improve singleDigit
  object betterSingleDigit {
    def unapply(arg: Int): Boolean = arg > -10 && arg < 10
  }

  val n2: Int = 1
  val mathProperty2 = n2 match {
    case betterSingleDigit() => "single digit"
    case betterEven() => "an even number"
    case _ => "no property"
  }
  println(mathProperty2)


}
