package in.kamranali.implicits

/**
  * Advance Scala Lesson 34 [Organising Implicits]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/11053818
  */

object OrganisingImplicits extends App {

  println(List(1,4,2,3).sorted)

  // `sorted` takes an implicit `def sorted[B >: A](implicit ord: Ordering[B])`

  // Scala looks for that implicit in scala.Predef which is already imported

  implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _) // <- This implicit will sort the list in reverse order and is picked by `sorted` method
  // `reverseOrdering` will take precedence over `Ordering` defined in Predef

  /**
    Potential Implicit used as Implicit parameters
    - val/var
    - objects
    - accessor methods = def with no parenthesis
   */

  /**
    Exercise
   */
  case class Person(name: String, age: Int)

  val persons = List(Person("Steve", 30), Person("Amy", 22), Person("John", 66))

  implicit val alphabetOrdering: Ordering[Person] = Ordering.fromLessThan((a,b) => a.name.compareTo(b.name) < 0)
  println(persons.sorted)

  /**
    Implicit Scopes (with decreasing priority)
    - Normal Scope = LOCAL SCOPE
    - imported Scope (e.g. what we did in Futures)
    - companion of all types involved in method signature
      - for e.g. in `sorted[B >: A](implicit ord: Ordering[B]): List` it will look into
        - List
        - Ordering Companion Object
        - All the types involved = A or any supertype

   */

  // In persons example, if we do
  object AnyObject {
    // implicit val alphabetOrdering: Ordering[Person] = Ordering.fromLessThan((a,b) => a.name.compareTo(b.name) < 0)

    // alphabetOrdering will NOT be in implicit Scope
  }

  // But in Person companion Object
  object Person {
    // implicit val alphabetOrdering: Ordering[Person] = Ordering.fromLessThan((a,b) => a.name.compareTo(b.name) < 0)

    // alphabetOrdering will be in implicit Scope
  }

  /*
    Best Practice for defining implicit

    Defining implicit value
    1 if there is a single possible vale for it => Define implicit value in companion object

    2 if there are MORE THAN 1 possible values for it BUT a single good one then,
      Define the good implicit in the companion and other implicit else where e.g. local scope Or other object

    3 if there are MORE THAN 1 possible + GOOD values => we should package them separately and make user explicitly import the right one.
   */


  object AlphabeticalNameOrdering {
    implicit val alphabetOrdering: Ordering[Person] = Ordering.fromLessThan((a,b) => a.name.compareTo(b.name) < 0)
  }

  object AgeOrdering {
    implicit val alphabetOrdering: Ordering[Person] = Ordering.fromLessThan((a,b) => a.age < b.age)
  }

  import AlphabeticalNameOrdering._
  println(persons.sorted)

  /**
    Exercise
   */

  case class Purchase(nUnits: Int, unitPrice: Double)

  object Purchase {
    // TotalPrice = most used (50%). Hence defined in Companion object
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
