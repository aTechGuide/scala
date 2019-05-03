package in.kamranali.fp

object AFunction extends App {

  /*
  In Functional Programming, We use Functions as First Class elements

  In Scala we can't because we are stuck with classes (JVM is designed that way). So what we do is
  we end up working with Function simulations (so to speak) like traits etc

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
  ALL SCALA FUNCTIONS are OBJECTS
   */

  val stringToInt = new Function[String, Int] {
    override def apply(v1: String): Int = v1.toInt
  }

  println(stringToInt("3") + 4)

  val adder = new Function2[Int, Int, Int] {
    override def apply(v1: Int, v2: Int): Int = v1 + v2
  }
  /*
  Type of adder is: Function2[Int, Int, Int] i.e. adder:Function2[Int, Int, Int]
  which has a syntactic sugar as ((Int, Int) => Int) i.e. adder:((Int, Int) => Int)
   */

  /*
  Function Returning Function
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
  They are called with multiple parameter list
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