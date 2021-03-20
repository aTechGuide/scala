package in.kamranali.AdvanceTypeSystem

/**
  * Advance Scala Lesson 46 [Type Members]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/11053858
  *
  */

object TypeMembers extends App {

  class Animal
  class Dog extends Animal
  class Cat extends Animal

  class AnimalCollection {
    type AnimalType //<- Declaring Abstract Type Member [Having defined this type I can then use it in variable or value definitions and in method signatures.]
    type BoundedAnimal <: Animal //<- Bounding Types[Upper bounded with Animal i.e. must extend Animal]
    type SuperBoundedAnimal >: Dog <: Animal //<- Lower Bounded in Dog and Upper Bounded in Animal
    type AnimalC = Cat //<- Type Aliases; `AnimalC` is another name for existing type `Cat `
  }

  // Now abstract type members are mostly for us to help the compiler do subtype inference for us so we won't see them too much in practice.

  val ac = new AnimalCollection
  // val dog: ac.AnimalType = ??? //<- We can NOT construct anything of `ac.AnimalType` because there is NO Constructor/Information that will allow compiler to build `AnimalType`

  // val cat: ac.BoundedAnimal = new Cat //<-Compilation Error
  // We do NOT know what `BoundedAnimal` is. It can be a Cat or Dog etc

  val pup: ac.SuperBoundedAnimal = new Dog //<- But another SuperType of Dog does NOT work because `SuperBoundedAnimal` is some super Type of Dog. And Compiler does NOT know if it is viable

  val cat: ac.AnimalC = new Cat //  Compiler equates AnimalC with a cat so it will know that the cat has a constructor so the association is fine.

  /**
    TYPE Alias also work outside
   */
  type CatAlias = Cat
  val anotherCat: CatAlias = new Cat

  // Type Alias are used when we have NAME COLLISIONS with lot of imported packages


  // Abstract Type members are sometimes used in APIs that look similar to generics
  trait MyList {
    type T
    def add(element: T): MyList
  }

  class NonEmptyList(value: Int) extends MyList {
    // Override both the members
    override type T = Int //<- Type is supplied explicitly
    override def add(element: Int): MyList = ???
  }

  /**
      .type
      - Using some values type as Type Alias
   */
  type CatsType = cat.type
  val newCat1: CatsType = cat //Limitation: I can NOT instantiate new elements of `CatsType`. I can only do association
  // new CatsType //<- Compilation Error
  // val newCat2: CatsType = new Cat //<- Compilation Error


  /**
    Exercise: Enforce the type to be applicable to SOME TYPES ONLY
   */
  // LOCKED
  trait MList {
    type A
    def head: A
    def tail: MList
  }

  // Following Code should Compile
  class IntegerList(hd: Int, tl: IntegerList) extends MList {
    override type A = Integer
    override def head: Integer = hd
    override def tail: IntegerList = tl
  }

  // Following Code should NOT Compile
  class StringList(hd: String, tl: StringList) extends MList {
    override type A = String
    override def head: String = hd
    override def tail: StringList = tl
  }

  /*
    Solution
   */

  trait ApplicableToNumbers {
    type A <: Number // Type A is upper bounded in Numbers
  }


  class IntegerList1(hd: Integer, tl: IntegerList1) extends MList with ApplicableToNumbers {
    override type A = Integer
    override def head: Integer = hd
    override def tail: IntegerList1 = tl
  }

  // Following Code should NOT Compile
  /*
      class StringList1(hd: String, tl: StringList1) extends MList with ApplicableToNumbers {
        override type A = String
        override def head = hd
        override def tail = tl
      } //<- Compilation Error: "overriding type A in trait ApplicableToNumbers with bounds <: Number;"
   */

}
