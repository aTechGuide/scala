package in.kamranali.OOP

/**
  * Basic Scala Lesson 18 [Generics]
  *
  * Ref
  * - https://www.udemy.com/course/rock-the-jvm-scala-for-beginners/learn/lecture/7660660
  * - https://stackoverflow.com/questions/19386964/i-want-to-get-the-type-of-a-variable-at-runtime
  * - https://docs.scala-lang.org/tour/lower-type-bounds.html
  */

object GenericsBasics extends App {

  /*
    Generic Type
      1. Can be applied to both Class and Trait
      2. Can be applied to methods
   */

  class MyList[A] { // A is generic Type

    // use Type A

  }

  val myIntList = new MyList[Int]
  val myStringList = new MyList[String]

  /*
    Generic Methods
   */

  // Companion object for class MyList
  // NOTE: object can NOT be type parametrised
  object MyList {

    // Generic Method
    def empty[A]: MyList[A] = new MyList[A]
  }

  val emptyListOfIntegers: MyList[Int] = MyList.empty[Int]

  /*
    Variance Problem
   */
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // Let's see what should be the relationship between List of Cat and List of Animal

  /**
    * Possibility 1. List[Cat] extends List[Animal], this behaviour is called COVARIANCE
    */

  class CovariantList[+A]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]

  // Now can we do, animalList.add(new Dog)  ???

  // As animalList is defined to be List of Animal so in theory we should be able to add any animal i.e. Dog in our case

  // But adding a Dog in a list of Cat will pollute out list

  // So this is HARD QUESTION

  // Answer to this question is we return List of Animals

  /**
    * Possibility 2. List[Cat] and List[Animal] are two different things, this behaviour is called INVARIANCE
    */

  class InvariantList[A]
  // val invariantAnimalList: InvariantList[Animal] = new InvariantList[Cat] // <- Will not compile
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal]

  /**
    *
    * Possibility 3. List[Cat] and List[Animal] are different,Hell NO !
    * this behaviour is called CONTRAVARIANCE
    */

  class ContrvariantList[-A]

  val contravariantList: ContrvariantList[Cat] = new ContrvariantList[Animal]

  // This is counter intuitive, How can we replace a list of Cat with a list of Animals

  // But look at following example

  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]

  // In this case, we asked for Trainer of Cat but we are given Trainer of Animals and that is a better option for us
  // It can also train a dog. It can also train a dinosaur.
  // And in particular it trains a cat which is what I want so I can use a trainer of animal to be a trainer of Cat

  /**
    Bounded Types
    We can use generic classes only for certain types that are either
    - a subclass of a certain type
        OR
    - super class of a different type
   */

  // Upper Bounded Type
  class Cage1[A <: Animal](animal: A) // class Cage only accepts A which are sub types of Animal

  val cage1: Cage1[Dog] = new Cage1(new Dog)

  // Lower Bounded Type
  class Cage2[A >: Animal](animal: A) // class Cage only accepts A which are super type of Animal

  /*
    Let's sum up all this
   */

  // Suppose MyList is covariant in Type A i.e.
  class MyNewList[+A] {

    // Let's see it add method
    //def add(element: A): MyList[A] = ??? // It fails to compile because of "Covariant type A occurs in contravariant position in type A of value element"
    // which is basically the technical version of HARD Question that we have asked above

    // We need to answer, If we have a list of Animal which is in fact a list of Cat so what if we add a dog there?

    // Answer is If in a List of Cat we add a Dog that will turn this new List into a List of Animals (i.e. it will convert it into a Generic Animal List)

    // Correct add method
    def add[S >: A](element: S): MyNewList[S] = new MyNewList[S] // i.e. if in list A, I put B which is super type of A then this List will turn into MyNewList `B`
    /*
      A = Cat
      B = Dog = which is an Animal. So B is basically an Animal
      So if we add B in List of A. It will turn into List of B i.e. Animal
     */

  }

  // Example
  val catList: MyNewList[Animal] = new MyNewList[Cat]

  // val newList  = catList.add[Dog](aDog) // Compilation Error
  val newList1  = catList.add(new Dog) // Here S is Animal
  val newList2 = catList.add(new String) // Here S is Object

  // println(newList1.getClass) // Prints -> class in.kamranali.OOP.GenericsBasics$MyNewList

  import scala.reflect.runtime.universe.TypeTag
  def f[T](v: T)(implicit ev: TypeTag[T]): Unit = println(ev.toString)

  f(newList1)
  f(newList2)

}
