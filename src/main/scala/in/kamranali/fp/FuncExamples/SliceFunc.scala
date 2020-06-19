package in.kamranali.fp.FuncExamples

/**
  * The slice method takes a start and end index and will use them to return a new collection with elements that are within the start and end index range.
  *
  * - def slice(from: Int, until: Int): Repr
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-slice-example/
  */
object SliceFunc extends App {

  val bag = Seq[Int](1, 2, 3, 4, 5)

  println(bag.slice(0,5)) // List(1, 2, 3, 4, 5)

  // With the slice method, you do not have to worry about IndexOutOfBoundsException.s
  println(bag.slice(0,10)) // List(1, 2, 3, 4, 5)

}
