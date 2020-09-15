package in.kamranali.fp

/**
  * Basic Scala Lesson 25 [Anonymous Function]
  *
  * Notes
  * - Refer One Note Scala > Concepts > Lambda
  *
  * Ref
  * - https://www.udemy.com/course/rock-the-jvm-scala-for-beginners/learn/lecture/7660692
  */

object AnonymousFunction extends App {

  /**
    Anonymous Functions / Lambda: Its a Syntactic Sugar for explicit implementation.
   */

  // Option 1
  val doubler1 = new Function1[Int, Int] {
    override def apply(x: Int): Int = x*2
  }

  // Option 2a (Anonymous Function/ LAMBDA)
  // Lambda expression instantiated Function1 with override 'apply' method
  val doubler2 = (x: Int) => x * 2

  // Option 2b (Anonymous Function/ LAMBDA)
  // Lambda expression instantiated Function1 with override 'apply' method.
  // As compiler knows what's the of doubler3. We don't have to specify type of Int
  val doubler3: Int => Int = x => x * 2

  /**
    Multiple params in lambda
   */
  val adder1 = (a: Int, b: Int) => a + b
  val adder2: (Int, Int) => Int = (a, b) => a + b

  /**
    no params
   */
  val returnThree: () => Int = () => 3

  /*
    We MUST Call lambdas with parenthesis
   */
  println(returnThree) // prints -> in.kamranali.fp.AnonymousFunction$$$Lambda$10/166239592@2d6e8792 (function itself)
  println(returnThree()) // prints -> 3 (actual Call)

  /**
    Curly braces with lambdas
   */
  val stringToInt = { (str: String) =>
    str.toInt
  }

  /**
    Syntactic Sugar
    Remember:
    - Each "_" stands for different parameter so we can NOT use _ multiple times for same parameter
   */
  val niceIncrementor1: Int => Int = x => x + 1
  val niceIncrementor2: Int => Int = _ + 1

  val niceAdder1: (Int, Int) => Int = (a,b) => a + b
  val niceAdder2: (Int, Int) => Int = _ + _


  // Supper Adder [Lambda Version]
  val superAdd = (x: Int) => (y: Int) => x + y
  println(superAdd(3)(4)) // 7
}
