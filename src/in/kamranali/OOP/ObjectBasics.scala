package in.kamranali.OOP

object ObjectBasics extends App {

  // SCALA DOES NOT HAVE CLASS LEVEL FUNCTIONALITY ("static")

  // Scala have `object` to create class level functionality
  object Person {

    // "static"/"class" Level Functionality
    val EYES = 2

    def canFly: Boolean = false
  }

  println(Person.EYES)
  println(Person.canFly)

 /*
 Scala Object is singleton instance.
 when we say ` object Person { } ` we define its type + its only instance
  */

  val mary = Person
  val john = Person

  println("mary == john: " + (mary == john).toString)

  /*
  COMPANIONS:
  Pattern of writing class and object with same name in same scope e.g. class Person and object Person are companions
   */

  class Person {
    // Instance Level Functionality
  }

  val person1 = new Person
  val person2 = new Person

  println(person1 == person2) // False

  /*
  Factory Method
   */

  object Person1 {

    // factory method
    def apply(mother: Person, father: Person) = new Person
  }

  val bobby = Person1.apply( person1, person2)
  // OR
  val sam = Person1(person1, person2)


  /*
  Scala Applications i.e. ` App `: It is a scala object with ` def main(args: Array[String]): Unit `
   */
}
