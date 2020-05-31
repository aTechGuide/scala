package in.kamranali.fp.FuncExamples

/**
  * The groupBy method takes a predicate function as its parameter and uses it to group elements by key and values into a Map collection.
  *
  * - groupBy[K](f: (A) ⇒ K): immutable.Map[K, Repr]
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-groupby-example/
  */

object GroupByFunc extends App {

  val names: Seq[String] = Seq[String]("kamran", "karan", "mayank", "palash", "piyush")

  // We are trying to map every element to a key via predicate method
  println( names.groupBy[Char](_.charAt(0))) // Map(k -> List(kamran, karan), m -> List(mayank), p -> List(palash, piyush))

}
