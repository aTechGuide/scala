package in.kamranali.implicits

object OrganisingImplicits extends App {

  println(List(1,4,2,3).sorted)

  // `sorted` takes an implicit `def sorted[B >: A](implicit ord: Ordering[B])`

  // Scala looks for that implicit in scala.Predef which is already imported

  implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _) // <- This implicit will sort the list in reverse order and is picked by `sorted` method
  // `reverseOrdering` will take precedence over `Ordering` defined in Predef

  /*
    Potential Implicit used as Implicit parameters
    - val/var
    - objects
    - accessor methods = def with no parenthesis
   */

  /*
    Exercise
   */
  case class Person(name: String, age: Int)

  val persons = List(Person("Steve", 30), Person("Amy", 22), Person("John", 66))

  implicit val alphabetOrdering: Ordering[Person] = Ordering.fromLessThan((a,b) => a.name.compareTo(b.name) < 0)
  println(persons.sorted)

  /*
    Implicit Scopes (with decreasing priority)
    - Normal Scope = LOCAL SCOPE
    - imported Scope (e.g. what we did in futures)
    - companion of all types involved in method signature
      - for e.g. in `sorted[B >: A](implicit ord: Ordering[B]): List` it will look into
        - List
        - Ordering
        - A or any superClass

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



}
