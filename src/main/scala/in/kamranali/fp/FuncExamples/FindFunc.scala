package in.kamranali.fp.FuncExamples

/**
  * The find method takes a predicate function as parameter and uses it to find the first element in the collection which matches the predicate.
  * It returns an Option and as such it may return a None for the case where it does not match any elements in the collection with the predicate function.
  *
  * - def find(p: (A) ⇒ Boolean): Option[A]
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-find-function/
  */

object FindFunc extends App {

  val bag: Seq[Int] = Seq[Int](1,2,3,4,5,6,7,8,9,10)

  println(bag find(_ == 5)) // Some(5)

  println(bag find(_ == 11)) // None

  println(bag find(_ == 11) getOrElse -1) // -1

}
