package in.kamranali.fp.FuncExamples

/**
  * The view method will create a non-strict version of the collection which means that the elements of the collection will only be made available at access time.
  *
  * - def view: TraversableView[A, Repr]
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-view-example/
  */
object ViewFunc extends App {


  val largeList = (1 to 1000000).filter(_ % 2 != 0).take(5)
  println(largeList) // Vector(1, 3, 5, 7, 9)

  // Running computation on the large list would require to eagerly build the elements of the list which could result in the typical OutOfMemory exception.
  // How to lazily create a large numeric range and take the first 10 odd numbers

  val lazyLargeList = (1 to 1000000).view.filter(_ % 2 != 0).take(5)
  println(lazyLargeList) // SeqViewFS(...)
  println(lazyLargeList.toList) // List(1, 3, 5, 7, 9)

}
