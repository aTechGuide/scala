package in.kamranali.fp.FuncExamples

/**
  *
  * The transpose method will pair and overlay elements from another collections into a single collection
  *
  * - def transpose[B](implicit asTraversable: A => /*<:<!!!*/ GenTraversableOnce[B]): CC[CC[B] @uncheckedVariance]
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-transpose-example/
  */

object TransposeFunc extends App {

  val items: Seq[String] = Seq[String]("Item-1", "Item-2", "Item-3", "Item-4")
  val prices: Seq[Int] = Seq[Int](1, 2, 3, 4)

  println(List(items, prices)) // List(List(Item-1, Item-2, Item-3, Item-4), List(1, 2, 3, 4))
  println(List(items, prices).transpose) // List(List(Item-1, 1), List(Item-2, 2), List(Item-3, 3), List(Item-4, 4))

}
