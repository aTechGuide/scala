package in.kamranali.OOP

object Exceptions extends App {

  /*
  Throwing Exceptions
   */
  // val aWeirdValue = throw new NullPointerException // <- an expression which throw NPE
  // aWeirdValue has `Nothing` Type.

  // throwable classes extend the Throwable class
  // Exception (something went wrong with program) and Error (something wrong with system) are major Throwable subTypes


  /*
  Catching Exceptions
   */
  def getInt(withExceptions: Boolean): Int =
    if (withExceptions) throw new RuntimeException("No INT")
    else 42

  try {
    // code that might fail
    getInt(true)
  } catch {
    case e: RuntimeException => println("Caught RunTime Exception")
  } finally {
    // code that will get executed no Matter what
    println("Executed Finally")
  }

  /*
  value of try, catch finally
   */

  val potentialFail = try {getInt(true)}
  catch {
    case e: RuntimeException => println("Caught RunTime Exception")
  }

  println(potentialFail) // potentialFail is anyVal (Unification of value returned from try ie. Int and catch ie. Unit)

  /**
   *
   * finally
   *   - Is optional
   *   - Doesn't influence return type of `try catch finally` block
   *   - used for side effects e.g. logging to a file
   */

  /**
   *
   * Custom Exceptions
   */
  class MyException extends Exception

  val newException = new MyException
  // throw newException // <- Crash the problem


}
