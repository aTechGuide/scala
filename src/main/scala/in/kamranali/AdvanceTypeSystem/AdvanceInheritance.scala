package in.kamranali.AdvanceTypeSystem

/**
  * Advance Scala Lesson 43 [Advance Inheritance]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/11053852
  */

object AdvanceInheritance extends App {

  /**
    *  Convenience
    */

  // Let's define Minimal API of Our (Imaginary) Library
  trait Writer[T] {
    def write(value: T): Unit
  }

  trait Closable {
    def close(status: Int): Unit
  }

  trait GenericStream[T] {
    def foreach(f: T => Unit): Unit
  }

  // Convenience => If we have GenericStream which mixes with other traits lets say Closable + Writer we can say,
  def processStream[T](stream: GenericStream[T] with Writer[T] with Closable): Unit = { //<- `GenericStream[T] with Writer[T] with Closable` is its own Type
    stream.foreach(println)
    stream.close(1)
  }

  /**
    * Diamond Problem
    */

  // Scenario 1
  trait Animal1 { def name: String }
  trait Lion1 extends Animal1 { override def name: String = "Lion"}
  trait Tiger1 extends Animal1 { override def name: String = "Tiger"}

  // trait Mutant1 extends Lion1 with Tiger1 //<- Compile because `name` is Unimplemented

  class Mutant1 extends Lion1 with Tiger1 { //<- Have to provide implementation of `name`
    override def name: String = "ALIEN"
  }

  // Scenario 2
  trait Animal2 {def name: String}
  trait Lion2 extends Animal2 { override def name: String = "Lion"}
  trait Tiger2 extends Animal2 { override def name: String = "Tiger"}

  class Mutant2 extends Lion2 with Tiger2 //<- Compiles even without an implementation to `name`; "DIAMOND PROBLEM"

  val mutant = new Mutant2
  println(mutant.name) // prints "Tiger"

  /*

     It prints "Tiger" because compiler converts above line to

     Mutant2 extends Animal2 with { override def name: String = "Lion"}
                with Animal2 with { override def name: String = "Tiger"}

     hence using last definition of name method.
     LAST OVERRIDE ALWAYS GETS PICKED

   */


  /**
    * Type Linearization
    *
    * - super Problem
    */

  trait Cold {
    def print: Unit = println("cold")
  }

  trait Green extends Cold {
    override def print: Unit = {
      println("green")
      super.print
    }
  }

  trait Blue extends Cold {
    override def print: Unit = {
      println("blue")
      super.print
    }
  }

  trait Red {
    def print: Unit = println("red")
  }

  class White extends Red with Green with Blue {
    override def print: Unit = {
      println("white")
      super.print
    }
  }

  val color = new White // Prints: white -> Blue -> Green -> Cold
  color.print

  /*
    Cold  = AnyRef with <Cold>
    Green = Cold with <Green> = AnyRef with <Cold> with <Green>
    Blue  = Cold with <Blue>  = AnyRef with <Cold> with <Blue>
    Red   = AnyRef with <Red>


    White = Red with Green with Blue with <white>
          = AnyRef with <Red>
            with (AnyRef with <Cold> with <Green>)
            with (AnyRef with <Cold> with <Blue>)
            with <white>

    Now the compiler says okay I have a bunch of types and a bunch of bodies.
    So it takes all these elements in turn and whatever it sees for the second time it just jumps over it.

          = AnyRef with <Red> with <Cold> with <Green> with <Blue> with <white>

    This is called Type Linearization for White
    Now in the context of Type Linearization the `super` keyword gets a whole new meaning

    So in the context of this particular Type Linearization for White,
    If we call super for example from the body of white,
    - this will take a look at the type immediately to the left [Which is Blue, So blue is printed]

    If we call super again it will look at the Type to the left [Which is Green, So green is printed]

    Calling Super Again will go to left i.e. Cold and Cold is printed

   */

}
