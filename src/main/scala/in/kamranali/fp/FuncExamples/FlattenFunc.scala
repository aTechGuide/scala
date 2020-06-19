package in.kamranali.fp.FuncExamples

/**
  * The flatten method will collapse the elements of a collection to create a single collection with elements of the same type.
  *
  * def flatten[B]: Traversable[B]
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-flatten-function-example/
  */
object FlattenFunc extends App {

  val bag = Seq(Seq(1,2,3), Seq(4,5,6))

  println(bag.flatten) // List(1, 2, 3, 4, 5, 6)

}
