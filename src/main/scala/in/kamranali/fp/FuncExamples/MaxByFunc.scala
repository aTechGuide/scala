package in.kamranali.fp.FuncExamples

/**
  * The maxBy method takes a predicate function as its parameter and applies it to every element in the collection to return the largest element.
  *
  * - def maxBy[B](f: (A) ⇒ B): A
  *
  * Similarly, We have
  * - def minBy[B](f: (A) ⇒ B): A
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-maxby-example/
  */
object MaxByFunc extends App {

  case class Item(name: String, price: Double)

  val bag = Seq[Item](Item("1", 1.5), Item("2", 1.2), Item("3", 3.0), Item("4", 2.5))

  println(bag maxBy(item => item.price)) // Item(3,3.0)

}
