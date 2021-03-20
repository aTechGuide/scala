package in.kamranali.fp.FuncExamples

/**
  * The zip method takes another collection as parameter and will merge its elements with the elements of the current collection
  * to create a new collection consisting of pairs or Tuple2 elements from both collections.
  * - def zip[B](that: GenIterable[B]): Iterable[(A, B)]
  *
  * The unzip method will unzip and un-merge a collection consisting of element pairs or Tuple2 into two separate collections.
  * - def unzip[A1, A2](implicit asPair: (A) ⇒ (A1, A2)): (CC[A1], CC[A2])
  *
  * Similarly, The unzip3 method will un-merge a collection consisting of elements as Tuple3 into three separate collections.
  * - def unzip3[A1, A2, A3](implicit asTriple: (A) ⇒ (A1, A2, A3)): (CC[A1], CC[A2], CC[A3])
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-zip-example/
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-unzip-example/
  *
  */
object ZipFunc extends App {

  val items: Seq[String] = Seq[String]("Item-1", "Item-2", "Item-3", "Item-4")
  val prices: Seq[Int] = Seq[Int](1, 2, 3, 4)

  val zipped = items zip prices
  val unzipped = zipped.unzip

  println(zipped) // List((Item-1,1), (Item-2,2), (Item-3,3), (Item-4,4))
  println(unzipped._1) // List(Item-1, Item-2, Item-3, Item-4)
  println(unzipped._2) // List(1, 2, 3, 4)

}

/**
  *
  * The zipWithIndex method will create a new collection of pairs or Tuple2 elements consisting of the element and its corresponding index.
  * - def zipWithIndex: Iterable[(A, Int)]
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-zipwithindex-example/
  */
object ZipWithIndexFunc extends App {

  val items: Seq[String] = Seq[String]("Item-1", "Item-2", "Item-3", "Item-4")

  println(items.zipWithIndex) // List((Item-1,0), (Item-2,1), (Item-3,2), (Item-4,3))

}
