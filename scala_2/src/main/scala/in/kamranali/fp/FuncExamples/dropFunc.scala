package in.kamranali.fp.FuncExamples

/**
  *
  * The drop method takes an integer parameter N and will return a new collection that does not contain the first N elements.
  *
  * - def drop(n: Int): Repr
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-drop-function/
  */
object dropFunc extends App {

  val bag = Seq("kamran", "ali", "nitb", "guna")
  println(bag drop 2) // List(nitb, guna)

}
