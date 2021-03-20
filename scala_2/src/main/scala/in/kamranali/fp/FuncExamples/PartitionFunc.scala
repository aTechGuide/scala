package in.kamranali.fp.FuncExamples

/**
  * The partition method takes a predicate function as its parameter and will use it to return two collections:
  * - one collection with elements that satisfied the predicate function and
  * - another collection with elements that did not match the predicate function.
  *
  * - def partition(p: (A) ⇒ Boolean): (Repr, Repr)
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-partition-example/
  */
object PartitionFunc extends App {

  val bag = Seq[Int](1,2,3,4,5,6,7,8)

  val part: (Seq[Int], Seq[Int]) = bag partition(_ < 5)

  println(part._1) // List(1, 2, 3, 4)
  println(part._2) // List(5, 6, 7, 8)

}
