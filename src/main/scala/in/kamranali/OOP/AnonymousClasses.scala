package in.kamranali.OOP

/**
  * Basic Scala Lesson 19 [Anonymous Classes]
  *
  * Ref
  * - https://www.udemy.com/course/rock-the-jvm-scala-for-beginners/learn/lecture/7660676
  */

object AnonymousClasses extends App {

  abstract class Animal {
    def eat: Unit
  }

  // Anonymous Class
  val funnyAnimal: Animal = new Animal { override def eat: Unit = println("Funny Animal") }

  println(funnyAnimal.getClass) // Prints class in.kamranali.OOP.AnonymousClasses$$anon$1

  // So what actually happened when we wrote `new Animal { override def eat: Unit = println("Funny Animal") }`
  // The compiler took `Animal { override def eat: Unit = println("Funny Animal") }`and created a class with a funny name like `in.kamranali.OOP.AnonymousClasses$$anon$1`
  // and actually instantiated that later and assigned that to funny animal.
  // This is called an anonymous class

  // Basically Compiler did the following

  /*
    It will create a new class with name `AnonymousClasses$$anon$1`

    class AnonymousClasses$$anon$1 extends Animal {
    override def eat: Unit = println("Funny Animal")
  }

    And then created `funnyAnimal` from it
    val funnyAnimal: Animal = new AnonymousClasses$$anon$1()

   */


  /*
    Anonymous Class from concrete Classes
    - Anonymous classes work for abstract and not abstract data types both
   */

  class Person(name: String) {
    def sayHi = s"Hi, from ${name}"
  }

  val jim: Person = new Person("jim") {
    override def sayHi: String = s"Hi, from jim"
  }

  println(jim.getClass) // Prints class in.kamranali.OOP.AnonymousClasses$$anon$2

}
