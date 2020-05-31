package in.kamranali.fp.FuncExamples

/**
  *
  * The sortBy method takes a predicate function and will use it to sort the elements in the collection.
  *
  * - def sortBy[B](f: (A) ⇒ B)(implicit ord: math.Ordering[B]): Repr
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-sortby-example/
  */

object SortByFunc extends App {

  case class Item(name: String, quantity: Int)

  val bag = Seq[Item](Item("1", 2), Item("2", 1), Item("3", 6), Item("4", 4))

  println(bag.sortBy(elem => elem.quantity)) // List(Item(2,1), Item(1,2), Item(4,4), Item(3,6))

}

/**
  * The sorted method will return a new collection with elements sorted by their natural order.
  *
  * - def sorted[B >: A](implicit ord: math.Ordering[B]): Repr
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-sorted-example/
  */
object SortedFunc extends App {

  val bag = Seq[Int](5,3,4,1,2)

  println(bag.sorted) // List(1, 2, 3, 4, 5)

}

/**
  * The sortWith method takes a predicate function and will use it to create a new collection where the elements are sorted by the predicate function.
  *
  * - def sortWith(lt: (A, A) ⇒ Boolean): Repr
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-sortwith-example/
  */
object SortWithFunc extends App {

  val bag = Seq[Int](5,3,4,1,2)

  println(bag.sortWith((a, b) => a > b)) // List(5, 4, 3, 2, 1)
  println(bag.sortWith((a, b) => a < b)) // List(1, 2, 3, 4, 5)

}
