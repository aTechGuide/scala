package in.kamranali.OOP

object AnonyousClasses extends App {

  abstract class Animal {
    def eat: Unit
  }

  // Anonymous Class
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("Funny Animal")
  }

  println(funnyAnimal.getClass)

  /*
  Anonymous Class from concrete Classes
   */

  class Person(name: String) {
    def sayHi = s"Hi, from ${name}"
  }

  val gim = new Person("Gim") {
    override def sayHi: String = s"Hi, from Gim"
  }

}
