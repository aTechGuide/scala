package in.kamranali.fp.FuncExamples

/**
  * The filter method takes a predicate function as its parameter and uses it to select all the elements in the collection which matches the predicate.
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-filter-filternot-function/
  */
object FilterFunc extends App {

  val bag: Seq[Int] = Seq[Int](1,2,3,4,5,6,7,8,9,10)

  println(bag filter (_ > 5)) // List(6, 7, 8, 9, 10)
  println(bag filterNot  (_ > 5)) // List(1, 2, 3, 4, 5)

}

/**
  * The withFilter method takes a predicate function and will restrict the elements to match the predicate function.
  * withFilter does not create a new collection while filter() method will create a new collection.
  *
  * - def withFilter(p: (A) ⇒ Boolean): FilterMonadic[A, Repr]
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-withfilter-example/
  */
object WithFilterFunc extends App {

  val bag: Seq[Int] = Seq[Int](1,2,3,4,5,6,7,8,9,10)

  /*
    When using the filter() method though a new collection is created with the filtered elements.
    If you would prefer to avoid the overhead of the new collection,
    you can make use of the withFilter() method to also restrict collection elements based on a given predicate.
   */

  val filtered = bag.withFilter(_ > 5)
  println(filtered) // scala.collection.TraversableLike$WithFilter@30c7da1e

  filtered.foreach(item => print(s"$item \t")) // 6 	7 	8 	9 	10



}
