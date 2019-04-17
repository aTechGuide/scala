package in.kamranali.OOP

object InheritanceBasics extends App {

  /*
  Scala has Single class Inheritance
   */

  class Animal {

    val creatureType = "Wild"
    def eat = println("Eating")

    private def sleep = println("Sleeping")

    protected def rave = println("Animal Raving")
  }

  class Cat extends Animal

  val cat = new Cat
  // cat.eat // "Eating"

  /*
  Sub Class only inherits non private members of super class
   */

  // cat.sleep <- Throws error

  /*
  Protected members
  Allows the protected method to be used inside the sub class as well but NOT accessible outside the subclass
   */

  class Dog extends Animal {

    def crunch = {
      rave
      println("Inside crunch and calling Rave")
    }
  }

  val dog = new Dog
  // dog.rave // <- rave is protected

  /*
  Extending class with Parameters
   */

  class Person(name: String, age: Int)

  class Adult(name: String, age: Int, idCard: String) extends Person(name, age)

    /*
    Over Riding
     */
  class Bull extends Animal {

      override val creatureType: String = "Domestic"

      override def eat: Unit = println("Bull is Eating")
      override def rave: Unit = println("Bull is Raving")

    }

  val bull: Bull = new Bull
  bull.eat // "Bull is Eating"
  bull.rave // "Bull is Raving"
  println(bull.creatureType)

  /*
  Type Substitution (Polymorphism)
  - A method call will always go to more over ridden version whenever possible
   */

  val unknownAnimal: Animal = new Bull
  unknownAnimal.eat

  /*
  Super
  - Used to refer method/field of parent class
   */

  /*
  Preventing Overrides
  - Use `final` Keyword on member
      - `final` on class makes it in-extendable e.g. String
  - Sealing the class by keyword `sealed`
    - It means we can extend the class in THIS File, preventing extension in other files
   */


}
