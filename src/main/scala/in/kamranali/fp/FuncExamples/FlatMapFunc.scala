package in.kamranali.fp.FuncExamples

/**
  * The flatMap method takes a predicate function, applies it to every element in the collection.
  *
  * It then returns a new collection by using the elements returned by the predicate function.
  *
  * The flatMap method is essentially a combination of the map method being run first followed by the flatten method.
  *
  * - def flatMap[B](f: (A) ⇒ GenTraversableOnce[B]): TraversableOnce[B]
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-flatmap-function/
  */

object FlatMapFunc extends App {

  val bag: Seq[Int] = Seq[Int](1,2,3)

  println(bag flatMap( elem => List.fill[Int](elem)(elem))) // List(1, 2, 2, 3, 3, 3)

  println(bag map( elem => List.fill[Int](elem)(elem))) // List(List(1), List(2, 2), List(3, 3, 3))

}
