package in.kamranali.OOP

object CaseClasses extends App {

  /*
  Case Class
  1. Class parameters are promoted to fields
  2. Sensible toString
  3. equals and hashCode implemented (out of Box)
  4. Have Handy Copy methods
  5. Have companion objects
  6. Are Serializable e.g. in Akka
  7. Have extractor patterns i.e. Case Classes can be used in Pattern Matching
  8. We also have `case object` similar to `case class` which acts like a class class except its an object. It has all the properties of case class except pt.5 (because they themselves are companion object)
   */
  case class Person(name: String, age: Int)

  val kamran1 = new Person("Kamran", 29)
  println(kamran1.name) // Class parameters are promoted to fields

  println(kamran1.toString) // Sensible toString


  val kamran2 = new Person("Kamran", 29)
  println(kamran1 == kamran2) // equals and hashCode implemented (out of Box)

  val kamran3 = kamran2.copy(age = 45) // Have Handy Copy methods
  println(kamran3)

  val thePerson = Person // `Person` is a companion object of case class
  val marry = Person("Marry", 23) // Same as ` Person.apply("Marry", 23) `.The companion object `Person` comes with apply() factory method
  // So companions method apply() method does the same thing as constructor

  /*

   */




}
