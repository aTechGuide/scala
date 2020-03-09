package in.kamranali.AdvanceTypeSystem

object FBoundedPolymorphism extends App {

  /*
    PROBLEM: How to FORCE a method in the Super Type to accept Current Type
   */
  trait Animal {
    def breed: List[Animal]
  }

  class Cat extends Animal {
    override def breed: List[Animal] = ??? //<- We want here List[Cat]
  }

  class Dog extends Animal {
    override def breed: List[Animal] = ??? //<- We want here List[Dog]
  }

  // Sol 1 - Naive
  trait Animal1 {
    def breed: List[Animal1]
  }

  class Cat1 extends Animal1 {
    override def breed: List[Cat1] = ??? //<- A Valid Code
  }

  class Dog1 extends Animal1 {
    override def breed: List[Dog1] = ??? //<- A Valid Code
  }

  // If by mistake if we do

  class Dog11 extends Animal1 {
    override def breed: List[Cat1] = ??? //<- A Valid but Wrong Code
  }

  // So how to make compiler force type correctness on us. So that we don't make mistake

  // Sol 2
  trait Animal2[A <: Animal2[A]] { // Recursive Type: F-Bounded Polymorphism
    def breed: List[Animal2[A]]
  }

  class Cat2 extends Animal2[Cat2] {
    override def breed: List[Animal2[Cat2]] = ??? //<- A Valid Code
  }

  class Dog2 extends Animal2[Dog2] {
    override def breed: List[Animal2[Dog2]] = ??? //<- A Valid Code

    //override def breed: List[Animal2[Cat2]] = ??? //<- Compilation Error
  }

  /*
    Usage in ORM, Frameworks and APIs
   */
  trait Entity[E <: Entity[E]] // ORMs

  /*
    Usage in Comparison
   */
  class Person extends Comparable[Person] {
    override def compareTo(o: Person): Int = ???
  }

  /*
    Limitations
   */
  class Crocodile extends Animal2[Dog2] {
    override def breed: List[Animal2[Dog2]] = ???
  }

  // So how to enforce the Class that I'm defining i.e. `Crocodile` and the Type i.e. `Dog2` that I'm annotating with are the Same

    // which introduces

  // Sol 3 - F Bounded Polymorphism + Self Types

  trait Animal3[A <: Animal3[A]] { self: A =>
    def breed: List[Animal3[A]]
  }

  class Cat3 extends Animal3[Cat3] {
    override def breed: List[Animal3[Cat3]] = ??? //<- A Valid Code
  }

  class Dog3 extends Animal3[Dog3] {
    override def breed: List[Animal3[Dog3]] = ??? //<- A Valid Code
  }

  // So if I say
//  class Crocodile extends Animal3[Dog3] {
//    override def breed: List[Animal3[Dog3]] = ???
//  } //<- Compilation Error

  // Limitation
  trait Fish extends Animal3[Fish]

  trait Shark extends Fish {
    override def breed: List[Animal3[Fish]] = ??? //<- its a `Animal3[Fish]` NOT `Animal3[Shark]`
  }

  // We can even say
  class Cod extends Fish {
    override def breed: List[Animal3[Fish]] = ??? //<- its a `Animal3[Fish]` NOT `Animal3[Shark]`
  }

  trait Shark1 extends Fish {
    override def breed: List[Animal3[Fish]] = List(new Cod) //<- Valid but WRONG
  }

  // And we have reached its FUNDAMENTAL LIMITATION. Once we bring class hierarchy down one level F Bounded Polymorphism Stops being effective

  /*
    Exercise: How to strictly enforce the breed method to return correct Type
   */
  // Using Type Classes (Sol 4)
  trait Animal4

  // Type Class description
  trait CanBreed[A] {
    def breed(a: A): List[A]
  }

  class Dog4 extends Animal4

  // Type Class Instance
  object Dog4 {
    implicit object DogsCanBreed extends CanBreed[Dog4] {
      override def breed(a: Dog4): List[Dog4] = List()
    }
  }

  // Converting Dog to implicit class which has a method which can receive an argument of `CanBreed[Dog]` as implicit param
  implicit class CanBreedOps[A](animal: A) {
    def breed(implicit canBreed: CanBreed[A]): List[A] = canBreed.breed(animal)
  }

  val dog4 = new Dog4
  dog4.breed
  /*
    Compiler will convert, `dog.breed` into following
      new CanBreedOps[Dog4](dog).breed(Dog4.DogsCanBreed)

    implicit value to pass to breed: Dog4.DogsCanBreed
   */

  // How compiler force to breed dog

  // Lets write Bad Class
  class Cat4 extends Animal4

  object Cat4 {
    implicit object CatsCanBreed extends CanBreed[Dog4] {
      override def breed(a: Dog4): List[Dog4] = List()
    }
  }

  val cat = new Cat4
  // cat.breed //<- Compilation Error: "could not find implicit value for parameter canBreed"
  // i.e. the `cat` companion object does NOT have the right Type Class instance Type
    // Basically compiler tried use `CanBreedOps` with `Cat4` Type AND FAILED to find an implicit `CanBreed[Cat4]`

  // Criticism of Sol 4
    // We do NOT have `breed` method inside `Animal` Or any of its Sub types

  // SOLUTION 5 Making Animal as Type class
    // Comment solution 4 and then uncomment Solution 5 for Solution 5 to work correctly
  /*
  trait Animal5[A] { // pure type classes
    def breed(a: A): List[A]
  }

  class Dog5
  object Dog5 {
    implicit object DogAnimal extends Animal5[Dog5] {
      override def breed(a: Dog5): List[Dog5] = List()
    }
  }

  implicit class AnimalOps[A](animal: A) {
    def breed(implicit animalTypeClassInstance: Animal5[A]): List[A] = animalTypeClassInstance.breed(animal)
  }

  val dog5 = new Dog5
  dog5.breed

  class Cat5
  object Cat5 {
    implicit object CatAnimal extends Animal5[Dog5] {
      override def breed(a: Dog5): List[Dog5] = List()
    }
  }

  val cat5 = new Cat5
  cat5.breed //<- Compilation Error: " could not find implicit value for parameter animalTypeClassInstance: ".
  // Because `CatAnimal` is extending the wrong type `Animal5[Dog5]`

  */

}
