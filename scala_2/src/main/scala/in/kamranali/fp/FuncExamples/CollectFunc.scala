package in.kamranali.fp.FuncExamples

/**
  *
  * def collect[B](pf: PartialFunction[A, B]): Traversable[B]
  *
  * The collect method takes a Partial Function as its parameter and applies it to all the elements in the collection to create a new collection which satisfies the Partial Function.
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-collect-function/
  */
object CollectFunc extends App {

  val data = Seq("kamran", 1, "Ali", 2)

  val strings = data.collect{
    case name:String => name
  }

  println(strings) // List(kamran, Ali)

  val numbers = data.collect{
    case num: Int => num
  }

  println(numbers) // List(1, 2)

}
