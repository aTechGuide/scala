package in.kamranali.fp.FuncExamples

/**
  * The filter method takes a predicate function as its parameter and uses it to select all the elements in the collection which matches the predicate.
  *
  * http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-filter-filternot-function/
  */
object FilterFunc extends App {

  val bag: Seq[Int] = Seq[Int](1,2,3,4,5,6,7,8,9,10)

  println(bag filter (_ > 5)) // List(6, 7, 8, 9, 10)

  println(bag filterNot  (_ > 5)) // List(1, 2, 3, 4, 5)

}
