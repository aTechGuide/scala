package in.kamranali.fp.FuncExamples

/**
  * The intersect method will find the common elements between two Sets.
  *
  * - def intersect(that: GenSet[A]): Repr
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-intersect-example/
  */
object IntersectFunc extends App {

  val bag1 = Set[String]("kamran", "ali")
  val bag2 = Set[String]("daud", "ali")

  println(bag1 intersect bag2) // Set(ali)

}
