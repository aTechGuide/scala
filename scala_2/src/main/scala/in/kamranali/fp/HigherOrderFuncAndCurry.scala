package in.kamranali.fp

import scala.annotation.tailrec

/**
  * Basic Scala Lesson 26, 27 [Higher Order Func And Curry]
  *
  * Advance Scala Lesson 14 [Currying and Partially Applied Functions]
  *
  *
  * Ref
  * - https://www.udemy.com/course/rock-the-jvm-scala-for-beginners/learn/lecture/7660688#content
  */

object HigherOrderFuncAndCurry extends App {

  /**
    Higher Order Function:
      Functions that either
      - Takes functions as parameters
      - Returns functions as results

      e.g. map, flatMap, filter
   */

  // Applying function f, n Times on value x
  @tailrec
  def nTimes(f: Int => Int, n: Int, x: Int): Int = {
    if (n <= 0) x
    else nTimes(f, n - 1, f(x))
  }

  val plusOne = (x: Int) => x + 1

  // println(nTimes(plusOne, 10, 1)) // prints 11

  /*
    Example 2
   */
  // Returning lambda which will apply function f, n times on value supplied to it
  def nTimesBetter(f: Int => Int, n: Int): Int => Int = {
    if(n <= 0) (x: Int) => x
    else (x: Int) => nTimesBetter(f, n-1)(f(x))
  }

  val plusTen = nTimesBetter(plusOne, 10)
  // println(plusTen(1)) // prints 11


  /**
    Curried Function
    - Helps in defining Helper functions that we want to use on various values
    - '=>' is right Associative
   */
  val superAdder: Int => Int => Int = (x: Int) => (y: Int) => x + y
  val add3 = superAdder(3) // add3 is a Lambda from Int => Int of form `y => y + 3`

  println(add3(2)) // prints 5
  println(superAdder(3)(10)) // prints 13


  // Scala allows the definition of Curried methods out of the box with multiple parameter lists.

  // A Curried Method

  /**
    Lifting = ETA - Expansion
   */

  // suppose we have following Curried Method
  def curriedAdder(x: Int)(y: Int): Int = x + y

  val add4: Int => Int = curriedAdder(4)
  // When we call method we need to pass all parameters, but in add4 we have converted a method into a Function Value of (Int => Int)
  // Basically we want to use Functional Values into HOFs, which is the whole purpose of functional programming
  // We can't use methods in HOFs unless they are transformed into function values.

  // In JVM languages, methods are part of instances of classes e.g. `curriedAdder` is part of `HigherOrderFuncAndCurry` class
  // and NOT instances of FunctionX themselves

  // So Transforming a METHOD to a FUNCTION by the compiler is called LIFTING (or ETA - Expansion)

  // Example 2
  def inc(x: Int) = x + 1
  List(1,2,3).map(inc) // <- Here compiler is doing an ETA Expansion for us converting `inc` Method into a Function. Then it uses function value on map

  // Basically compiler will rewrite it as
  List(1,2,3).map(x => inc(x))

  /**
    Partial Function Application
    - It implies forcing compiler to do ETA Expansion when we want
   */
  // Let's define function value from `curriedAdder`
  val add5 = curriedAdder(5) _ // <- This `_` tell compiler to do ETA expansion i.e. to turn `curriedAdder` into a function value of "Int => Int" after you've applied first parameter list


  /**
    Exercise
   */

  // Exercise 1
  def toCurry(f: (Int, Int) => Int): Int => Int => Int =
    x => y => f(x, y)

  def fromCurry(f: Int => Int => Int): (Int, Int) => Int =
    (x, y) => f(x)(y)


  // Exercise 2
  def compose[A, B, T](f: B => A, g: T => B): T => A =
    x => f(g(x))

  def andThen[A, B, C](f: A => B, g: B => C): A => C =
    x => g(f(x))

  // Exercise 3
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int): Int = x + y
  def curriedAddMethod(x: Int)(y: Int): Int = x + y

  // Let's define multiple implementations of add7: Int => Int = y => 7 + y

  // 1. simplest
  val add7_1: Int => Int = (x: Int) => simpleAddFunction(7, x)

  // 2.
  val add7_2: Int => Int = simpleAddFunction.curried(7)

  // 6.
  val add7_6: Int => Int = simpleAddFunction(7, _:Int) // works as well (Basically `_` forces the compiler to do EAT expansion)

  // 3.
  val add7_3: Int => Int = curriedAddMethod(7) _ // <- Partially applied function with ETA Expansion

  // 4.
  val add7_4: Int => Int = curriedAddMethod(7)(_) // PAF alternative syntax

  // 5.
  val add7_5: Int => Int = simpleAddMethod(7, _: Int) // alternative syntax for turning methods into function values (Forces compiler for ETA expansion)


  /**
    Underscores
   */
  def concatenator(a: String, b: String, c: String): String = a + b + c

  val insertName: String => String = concatenator("Hello, I'm ", _:String, ", How are you") // <- 'insertName' is a Function Value. from ETA expansion
  // x: String => concatenator("Hello, I'm ", x, ", How are you")

  println(insertName("Daniel")) // Hello, I'm Daniel, How are you

  val fillInTheBlanks: (String, String) => String = concatenator("Hello", _:String, _:String)
  // (x, y) => concatenator("Hello", x, y)

  println(fillInTheBlanks(" Kamran,", " Scala is awesome")) // Hello Kamran, Scala is awesome

  // Exercise
  def curriedFormatter(c: String)(x: Double): String = c.format(x)

  // Let's create Sub-Functions from curriedFormatter
  // 'standardFormat' is a function from Double to String
  val standardFormat: Double => String = curriedFormatter("%4.2f") // here specifying type of `standardFormat` is necessary
  val preciseFormat = curriedFormatter("%10.8f") _ // Lifting

  // PS: We have to specify type of `standardFormat` and `preciseFormat` else the code will not compile
  // The compiler complains because it will not know what type the `standardFormat` function will have
  // The proper way to do that is to use partial function applications.

  println("Standard Format: " + standardFormat(Math.PI)) // Standard Format: 3.14
  println("Precise Format: " + preciseFormat(Math.PI)) // Precise Format: 3.14159265

  /**
    Difference Functions Vs Methods
   */

  def byName(n: => Int): Int = n + 1  // By Name Parameters
  def byFunction(f: () => Int): Int = f() + 1 // 0 - lambda (Nothing to Int)

  def method: Int = 42
  def parenMethod(): Int = 42


  byName(23) // OK
  byName(method) // OK [ Because the method will be evaluated to its call i.e. 42]
  byName(parenMethod()) // OK

  byName(parenMethod) // OK but beware; parenMethod method is actually called and is equivalent to `byName(parenMethod())`
  // ^ we are NOT calling `byName` as if `byName` is a HOF accepting a function

  // The confusion that arises here in the situation is that people confuse the by name parameter (: =>) with a higher order function
  // And they expect byName with a function i.e. `byName(parenMethod)`, to actually use the function itself inside.
  // But in reality only calls the method and byName actually uses that value not the function itself.
  // So be very careful about the distinction between by name parameters (: =>) and function parameters.

  //byName(() => 2) // Compilation Error: By Name argument of value type is NOT same as Function parameter

  byName((() => 2)()) // OK because we are calling the supplied function

  // byName(parenMethod _) // NOT OK because `parenMethod _` is an expression i.e Function Value which is not a substitute for By Name Parameter


  //byFunction(45) // NOT OK
  // byFunction(method) // NOT OK because method is evaluated to value 42. NOT ETA Expansion
  // Generally methods without parenthesis can't be pass to HOFs

  byFunction(parenMethod) // Compiler does ETA Expansion
  byFunction(parenMethod _) // Works but warning of unnecessary

  byFunction(() => 45)
}
