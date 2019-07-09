package in.kamranali.AdvanceTypeSystem

object TypeMembers extends App {

  class Animal
  class Dog extends Animal
  class Cat extends Animal

  class AnimalCollection {
    type AnimalType //<- Declaring Abstract Type Member
    type BoundedAnimal <: Animal //<- Bounding Types
    type SuperBoundedAnimal >: Dog <: Animal //<- Lower Bounded in Dog and Upper Bounded in Animal
    type AnimalC = Cat //<- Type Aliases; `AnimalC` is another name for existing type `Cat `
  }

  val ac = new AnimalCollection
  // val dog: ac.AnimalType = ??? //<- We can NOT construct anything of `ac.AnimalType` because there is NO Constructor/Information that will allow compiler to build `AnimalType`

  // val cat: ac.BoundedAnimal = new Cat //<-Compilation Error
  // We do NOT know what `BoundedAnimal` is

  val pup: ac.SuperBoundedAnimal = new Dog //<- But another SuperType of Dog does NOT work because `SuperBoundedAnimal` is some super Type of Dog.
  // But for other super Type of Dog Compiler does NOT know if it is viable

  val cat: ac.AnimalC = new Cat

  /*
    TYPE Alias also work outside
   */
  type CatAlias = Cat
  val anotherCat: CatAlias = new Cat

  // Type Alias are used when we have NAME COLLISIONS with lot of imported packages


  // Alternate to Generics
  trait MyList {
    type T
    def add(element: T): MyList
  }

  class NonEmptyLost(value: Int) extends MyList {
    // Override both the members
    override type T = Int //<- Type is supplied explicitely
    override def add(element: Int): MyList = ???
  }

  /*
      .type: Using some values type as Type Alias
   */
  type CatsType = cat.type
  val newCat: CatsType = cat //Limitation: I can NOT instantiate new elements of `CatsType`. I can only do association
  // new CatsType //<- Compilation Error


  /*
    Exercise: Enforce the type to be applicable to SOME TYPES ONLY
   */
  // LOCKED
  trait MList {
    type A
    def head: A
    def tail: MList
  }

  // Following Code should Compile
  class IntList(hd: Int, tl: CustomList) extends MList {
    override type A = Int
    override def head = hd
    override def tail = tl
  }

  // Following Code should NOT Compile
  class CustomList(hd: String, tl: CustomList) extends MList {
    override type A = String
    override def head = hd
    override def tail = tl
  }

  /*
    Solution
   */

  trait ApplicableToNumbers {
    type A <: Number // Type A is upper bounded to Numbers
  }


  class IntList1(hd: Int, tl: CustomList) extends MList {
    override type A = Int
    override def head = hd
    override def tail = tl
  }

  // Following Code should NOT Compile
//  class CustomList1(hd: String, tl: CustomList) extends MList with ApplicableToNumbers {
//    override type A = String
//    override def head = hd
//    override def tail = tl
//  } //<- Compilation Error: "overriding type A in trait ApplicableToNumbers with bounds <: Number;"
}
