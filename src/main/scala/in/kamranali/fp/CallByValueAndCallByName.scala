package in.kamranali.fp

object CallByValueAndCallByName extends App {

  def something = {
    println("Something")
    1
  }

  def callByName(n: Int): Unit = {
    println("Value passed is: ", n)
    println("Value passed is: ", n)
  }

  def callByValue(n: => Int): Unit = {
    println("Value passed is: ", n)
    println("Value passed is: ", n)
  }

  callByName(something) // It computes the passed-in expression's value before calling the function, thus the same value is accessed every time

  callByValue(something) // It recompute the passed-in expression's value every time it is accessed. Hence, prints `something` twice


}
