package in.kamranali.implicits

object ImplicitBestPractice extends App {

  /*
    When defining implicit

    Defining implicit value
    1 if there is a single possible vale for it => Define implicit value in companion object
    2 if there are MORE THAN 1 possible values for it BUT a single good one => Define the good implicit in the companion and other implicit else where
      e.g. local scope Or other object
    3 if there are MORE THAN 1 possible + GOOD values => we should package them separately and make user explicitly import the right one.
   */

  // point 3

  case class Person(name: String, age: Int)

  val persons = List(Person("Steve", 30), Person("Amy", 22), Person("John", 66))

  object AlphabeticalNameOrdering {
    implicit val alphabetOrdering: Ordering[Person] = Ordering.fromLessThan((a,b) => a.name.compareTo(b.name) < 0)
  }

  object AgeOrdering {
    implicit val alphabetOrdering: Ordering[Person] = Ordering.fromLessThan((a,b) => a.age < b.age)
  }

  import AlphabeticalNameOrdering._

  println(persons.sorted)

  /*
    Exercise
   */

  case class Purchase(nUnits: Int, unitPrice: Double)

  object Purchase {
    // TotalPrice = most used (50%)
    implicit  val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a,b) => a.nUnits * a.unitPrice < b.nUnits * b.unitPrice)
  }

  object unitCountOrdering {
    // By Unit count (25% used)
    implicit  val unitCountOrdering: Ordering[Purchase] = Ordering.fromLessThan((a,b) => a.nUnits < b.nUnits)
  }

  object unitPriceOrdering {
    // By Unit price (25% used)
    implicit  val unitPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a,b) => a.unitPrice < b.unitPrice)
  }








}
