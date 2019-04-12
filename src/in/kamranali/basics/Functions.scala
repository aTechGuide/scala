package in.kamranali.basics

object Functions extends App {

  /*
  Simple Function
  1. Compiler can infer the return type of Function
   */
  def aFunc(a: String, b: Int): String = a + " " + b

  // Calling a function is also an expression
  println("hello", 3)

  /*
  Function with Side Effects
   */
  def aFuncWithSideEffects(a: String): Unit = print(a)

  /*
  Parameterless Functions
   */
  def parameterLessFunction(): Int = 42

  // Both ways are correct but without parentheses we will see a warning
  println(parameterLessFunction)
  println(parameterLessFunction())

  /*
    Use Recursive Functions in place of loops
    1. Recursive Functions needs return type else compiler will complain
   */
  def aRepeatedFunction(aString: String, n: Int): String = {
    if (n == 1) aString
    else aString + aRepeatedFunction(aString, n - 1)
  }

  println(aRepeatedFunction("Hello", 3))

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

  print(fibonacci(8))

}
