package in.kamranali.fp

/**
  * Advance Scala Lesson 15 [LazyEvaluation]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/10937384
  */

object LazyEvaluation extends App {

  // val x1: Int = throw new RuntimeException() // Crash the program

  lazy val x2: Int = throw new RuntimeException() // DOESN'T  Crash the program

  /*
    Lazy Values are evaluated when they are used for the first time (by need basis)
   */

  //println(x2) // Crash the program

  // Note: Once the value is evaluated then the same value will stay assigned to that same name
  lazy val x3: Int = {
    println("Hello")
    42
  }

  println(x3) // x3 is evaluated. Prints `Hello`.
  println(x3) // x3 is NOT Re-evaluated again

  /*
    Examples of implications: With Side Effects
   */
  def sideEffectCondition: Boolean = {
    println("Boo")
    true
  }

  def simpleCondition: Boolean = false

  lazy val lazyCondition = sideEffectCondition

  println(if (simpleCondition && lazyCondition) "yes" else "no" ) // "no" is printed. Side effect is NOT printed


  /*
    Examples of implications: In Conjunction with call by name
   */
  def byNameMethod1(n: => Int): Int = n + n + n + 1

  def retrieveMagicValue = {
    // side effect or a long computation
    println("Waiting")
    Thread.sleep(1000)
    42
  }

  println(byNameMethod1(retrieveMagicValue)) // 3 sec Waiting time

  // it doesn't make sense to use call by name and still evaluate it multiple times unnecessarily.


  // use lazy vals
  def byNameMethod2(n: => Int): Int = {
    lazy val t = n // Only evaluated once i.e. CALL BY NEED. Then use it when needed
    t + t + t + 1
  }

  println(byNameMethod2(retrieveMagicValue)) // 1 sec waiting

  /*
    Examples of implications: Filtering with Lazy vals
   */
  def lessThan30(i: Int): Boolean = {
    println(s"$i is less than 30?")
    i < 30
  }

  def greaterThan20(i: Int): Boolean = {
    println(s"$i is greater than 20?")
    i > 20
  }

  val numbers = List(1, 25, 40, 5, 23)
  val lt30 = numbers.filter(lessThan30) // List(1, 25, 5, 23)
  val gt20 = lt30.filter(greaterThan20) // List(25, 23)

  println
  println(gt20)

  val lt30lazy = numbers.withFilter(lessThan30) // lazy vals under the hood
  val gt20Lazy = lt30lazy.withFilter(greaterThan20)


  // `withFilter` uses lazy values under the hood
  println()
  println(gt20Lazy) // <- `lessThan30` and `greaterThan20` methods are never called in lazy version

  gt20Lazy.foreach(println) // <- this will force the filtering to take place
  // If we look at result we can see, Predicates are being check on bi need basis

  /*
    For-comprehension use withFilter with guards
   */

  for {
    a <- List(1,2,3) if a % 2 == 0 // use lazy vals
  } yield a + 1

  // Above For-comprehension translates to
  List(1,2,3).withFilter(_ % 2 == 0).map(_ + 1) // List[Int]

}
