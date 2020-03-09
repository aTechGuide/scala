package in.kamranali.basics

import scala.annotation.tailrec

object Functions extends App {

  /*
  Simple Function
  1. Compiler can infer the return type of Function by looking at implementation
  CAVEAT: For recursive functions, compiler can't figure out the return type
   */
  def aFunc(a: String, b: Int): String = a + " " + b

  // Calling a function is also an expression
  //println("hello", 3)

  /*
  Function with Side Effects
   */
  def aFuncWithSideEffects(a: String): Unit = print(a)

  /*
  Parameter less Functions
   */
  def parameterLessFunction(): Int = 42

  // Both ways are correct but without parentheses we will see a warning
  // println(parameterLessFunction)
  // println(parameterLessFunction())

  /*
    Use Recursive Functions in place of loops
    1. Recursive Functions needs return type else compiler will complain
   */
  def aRepeatedFunction(aString: String, n: Int): String = {
    if (n == 1) aString
    else aString + aRepeatedFunction(aString, n - 1)
  }

  // println(aRepeatedFunction("Hello", 3))

  /*
  Auxiliary Function inside a Function
   */
  def aBigFunction(n: Int): Int = {

    def smallFunction(a: Int, b: Int): Int = a + b

    smallFunction(n , n-1) // Return type of big function is value returned from smallFunction
  }

  def fibonacci(n: Int): Int = {
    if (n <= 2) 1
    else fibonacci(n - 1) + fibonacci(n - 2)
  }

  // print(fibonacci(8))

  /*
  Tail Recursion
   */
  // To understand tail recursion lets take the example of Factorial Function

  def factorial(x: Int): BigInt =
    if (x <=1 ) return 1
    else {
      x * factorial(x - 1)
    }

  // if we write factorial(50000) the program crashes because of Stack Overflow Exception
  // print("\nCalling Factorial" + factorial(50000))

  // Let's define factorial again

  def betterFactorial(x: Int): BigInt = {

    @tailrec // <- This tells scala that this function should be tail recursive else throw error
    def factorialHelper(x: Int, accumulator: BigInt): BigInt = {
      if (x <=1 ) return accumulator
      else factorialHelper(x - 1, x * accumulator)
    }

    factorialHelper(x, 1)
  }

  // if we write betterFactorial(5000) it will work.
  //print("Better Factorial: " + betterFactorial(50000))

  // What happened ???

  // Trick is when `else factorialHelper(x - 1, x * accumulator)` is written as a LAST EXPRESSION in the code path.

  // This allows scala to preserve the same stack frame and not use additional stack frame for
  // recursive calls.

  // If scala doesn't need to store intermediate data to be used later. It will not use additional stack frame

  // This phenomenon is known as TAIL RECURSION

  // Trick to TAIL RECURSION is to use ACCUMULATORs to store intermediate results rather than calling function recursively

  // In previous factorial Function, Scala needs to preserve the stack frame because
  // in `x * factorial(x - 1)` it needs to calculate intermediary step `factorial(x - 1)`
  // and then multiple it with `x`

  /*
  Call by Value VS Call by Name
   */

  def callByValue(x: Long): Unit = {
    println("By value: " + x)
    println("By value: " + x)
  }

  def callByName(x: => Long): Unit = {
    println("By Name: " + x)
    println("By Name: " + x)
  }

  callByValue(System.nanoTime())
  callByName(System.nanoTime())

  // When we call callByValue, Value of ` System.nanoTime() ` is computed before the function evaluates and same value is used in function definition.

  // when we call callByName, ` System.nanoTime() ` Expression is passed literally as is. That expression is evaluated every time.
  // '=>' parameter delays the evaluation of the expression passed as parameter

  /*
  Default Args
   */

  // Let's take the example of Tail Recursive Factorial

  def tailRecursivefactorial(x: Int, accumulator: Int): Int = {
    if (x <=1 ) return accumulator
    else tailRecursivefactorial(x - 1, x * accumulator)
  }

  // Call tailRecursivefactorial(5, 1)

  // When we call this method, value of accumulator is always 1. Basically ` accumulator ` is polluting our method signature.

  // One way out is to wrap it into a function accepting only `x` (Which we did in ` betterFactorial `)

  // Another way is to provide default values to some parameters. e.g.

  def tailRecursivefactorialDefaultParam(x: Int, accumulator: Int = 1): Int = {
    if (x <=1 ) return accumulator
    else tailRecursivefactorial(x - 1, x * accumulator)
  }

  // Call tailRecursivefactorialDefaultParam(5)

  // If we have following function

  def savePic(format : String = "jpg", width: Int = 1920, height: Int = 1080) = print("Saving Picture")

  // If we want to pass only width then we have only two options
  // 1. Pass all the leading arguments e.g. savePic("bmp", 800)
  // 2. name the arguments e.g. savePic(width = 800)



}
