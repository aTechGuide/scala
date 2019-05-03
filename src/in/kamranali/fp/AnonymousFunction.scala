package in.kamranali.fp

object AnonymousFunction extends App {

  /*
  Anonymous Functions/ Lambda: Its a Syntactic Sugar for explicit implementation.
   */

  // Option 1
  val doubler1 = new Function1[Int, Int] {
    override def apply(x: Int): Int = x*2
  }

  // Option 2a (Anonymous Function/ LAMBDA)
  val doubler2 = (x: Int) => x*2 // Lambda expression instantiated Function1 with override 'apply' method

  // Option 2b (Anonymous Function/ LAMBDA)
  val doubler3: Int => Int = x => x*2 // Lambda expression instantiated Function1 with override 'apply' method. As compiler knows what's the of doubler3. We don't have to specify type of Int

  /*
  Multiple params in lambda
   */
  val adder1 = (a: Int, b: Int) => a + b
  val adder2: (Int, Int) => Int = (a, b) => a + b

  /*
  no params
   */
  val returnThree = () => 3

  /*
  We MUST Call lambdas with paranthesis
   */
  println(returnThree) // in.kamranali.fp.AnonymousFunction$$$Lambda$10/166239592@2d6e8792 (function itself)
  println(returnThree()) // 3 (call

  /*
  Curly braces with lambdas
   */
  val stringToInt = {(str: String) =>
    str.toInt
  }

  /*
  Syntactic Sugar
   */
  val niceIncrementor1: Int => Int = x => x + 1
  val niceIncrementor2: Int => Int = _ + 1

  val niceAdder1: (Int, Int) => Int = (a,b) => a + b
  val niceAdder2: (Int, Int) => Int = _ + _
}
