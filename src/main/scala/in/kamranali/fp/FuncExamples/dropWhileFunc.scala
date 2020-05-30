package in.kamranali.fp.FuncExamples

/**
  * The dropWhile method takes a predicate function parameter that will be used to drop certain elements in a collection
  * which satisfies the predicate function.
  *
  * The "dropping" process, or removal of elements, will stop as soon as it encounters an element that does not match the predicate function.
  *
  *  - def dropWhile(p: A => Boolean): Repr
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-drop-function/
  */
object dropWhileFunc extends App {

  val bag = Seq("", "", "", "", "kamran", "ali", "nitb", "guna")
  println(bag.dropWhile(_.isEmpty)) // List(kamran, ali, nitb, guna)

}
