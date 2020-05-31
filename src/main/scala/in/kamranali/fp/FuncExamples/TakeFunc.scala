package in.kamranali.fp.FuncExamples

/**
  * def take(n: Int): Repr
  *     The take method takes an integer N as parameter and will use it to return a new collection consisting of the first N elements.
  *
  *
  * def takeRight(n: Int): Repr
  *     The takeRight method takes an integer N as parameter and will use it to return a new collection consisting of the last N elements.
  *
  *
  * def takeWhile(p: (A) ⇒ Boolean): Repr
  *     The takeWhile method takes a predicate function and will use it to return a new collection consisting of elements which match the predicate function.
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-take-example/
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-takeright-example/
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-takewhile-example/
  */
object TakeFunc extends App {

  val bag = Seq[Int](1, 2, 3, 4, 5)

  println(bag take 3) // List(1, 2, 3)
  println(bag takeRight 3) // List(3, 4, 5)
  println(bag takeWhile( elem => elem < 4)) // List(1, 2, 3)

}
