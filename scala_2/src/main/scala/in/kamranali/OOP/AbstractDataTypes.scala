package in.kamranali.OOP

object AbstractDataTypes extends App {

  /*
  Abstract Fields / Methods
  - Class that contains unimplemented Fields and Methods + implemented Fields and Methods
  - Can't be instantiated
   */

  abstract class Animal {
    val creatureType: String // `creatureType` is abstract as we have not supplied any value
    def eat: Unit
  }

  class Dog extends Animal {
    val creatureType: String = "Canine"
    def eat: Unit = println("Dog Eating")
  }

  /*
  Traits
  - Ultimate Abstract DataTypes is Scala
  - It contains both implemented and unimplemented members
   */

  trait Carnivore {
    def eat(animal: Animal): Unit

    val preferedMeal: String = "Meat"
  }

  trait ColdBlooded

  class Crcodile extends Animal with Carnivore with ColdBlooded {

    val creatureType: String = "Croc"
    def eat(animal: Animal): Unit = println(s"I'm a croc and I'm eating ${animal.creatureType}")
    def eat: Unit = println("Croc Eating")
  }

  val dog = new Dog
  val crocodile = new Crcodile

  crocodile.eat(dog)


  /*
  Traits Vs Abstract
  1. Traits don't have constructor parameters
  2. We can only extend one class But can inherit multiple Traits
  3. traits are behaviours, abstract class is a "thing"
   */

  /*
  In scala
  - All class extents AnyRef (which extends Any)
  - All primitives e.g. Int, Unit etc extends scala.AnyVal

  Scala.Null is derived from all intermediate chain of references
  scala.Nothing is subType of every single thing
   */
}
