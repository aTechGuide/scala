package in.kamranali.fp

/**
  * Basic Scala Lesson 24 [What's a Function, Really]
  *
  * - We will learn basics Or philosophy of Curried Functions
  *
  * Ref
  * - https://www.udemy.com/course/rock-the-jvm-scala-for-beginners/learn/lecture/7660680
  */
object AFunction extends App {

  /*
    In Functional Programming, We use Functions as First Class Programming Elements
    - Pass functions as parameters Or retrun Function as results
    - use functions are values
    [i.e. we want to work with functions like we work with plain values]

    The problem is that we come from an Object-Oriented world.
    And when we come from an Object-Oriented world then basically everything is an object that means it's an instance of some kind of class.
    This is how the JVM was originally designed for Java for instances of classes and nothing else.

    So the only way that we could simulate functional programming is to use classes and instances of those classes.
    So what we do is we end up working with Function simulations (so to speak) like traits etc


   */

  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element*2
  }

  println(doubler(2)) // We are calling doubler as it is a Function. "apply magic"
  // Actually doubler is an instance of Function like class
  println(doubler.apply(2))

  /*
  Scala supports multiple function by default e.g. Function1[A,B]
  Scala supports these function Types upto 22 parameters

  Bottom Line
  ALL SCALA FUNCTIONS are OBJECTS i.e. Instance of classes
   */

  val stringToInt = new Function[String, Int] {
    override def apply(v1: String): Int = v1.toInt
  }

  println(stringToInt("3") + 4)

  val adder: ((Int, Int) => Int) = new Function2[Int, Int, Int] {
    override def apply(v1: Int, v2: Int): Int = v1 + v2
  }
  /*
  Type of adder is: Function2[Int, Int, Int] i.e. adder:Function2[Int, Int, Int]
  which has a syntactic sugar as ((Int, Int) => Int) i.e. adder:((Int, Int) => Int)
   */

  /*
    Function Returning Function [Curried Function]
   */
  val superAdder: Function1[Int, Function1[Int, Int]] = new Function[Int, Function1[Int, Int]] {

    override def apply(x: Int): Int => Int = new Function[Int, Int] {
      override def apply(y: Int): Int = x + y // x is visible in Functional implementation
    }
  }

  val adder3 = superAdder(3)
  println(adder3(9)) // 12

  /*
    Curried Function
    - They have the property that they can be called with multiple parameter lists just by their mere definition.
    - So a Curried function actually receives some kind of parameter and returns another function which receives parameters.
    - Basically they are called with multiple parameter list to get the final result
   */
  println(superAdder(3)(4)) // 7

}

class Action1 {
  def execute(ab: Int): String = ab.toString
}

// What we can do is to abstract away as much as possible. Action1 can be written as Action2 in more generalised way

trait Action2[A, B] {
  def execute(ab: A): B
}

// Let's add the magic of apply in below class

trait MyFunction[A, B] {
  def apply(element: A): B
}