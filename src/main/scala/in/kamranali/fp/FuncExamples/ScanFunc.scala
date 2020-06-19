package in.kamranali.fp.FuncExamples


/**
  * The scan method takes an associative binary operator function as parameter and will use it to collapse elements from the collection to create a running total from each element.
  * Similar to the fold method, scan allows you to also specify an initial value.
  *
  * - def scan[B >: A, That](z: B)(op: (B, B) ⇒ B)(implicit cbf: CanBuildFrom[Repr, B, That]): That
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-scan-example/
  */

object ScanFunc extends App {

  val bag = Seq[Int](1, 2, 3, 4, 5)

  /*
    Scan method iterations
      0 + 1             =  1
      1 + 2             =  3
      1 + 2 + 3         =  6
      1 + 2 + 3 + 4     = 10
      1 + 2 + 3 + 4 + 5 = 15
   */
  println(bag.scan(0)(_ + _)) // List(0, 1, 3, 6, 10, 15)

}

/**
  *
  * The scanLeft method takes an associative binary operator function as parameter and will use it to collapse elements from the collection to create a running total.
  * The order for traversing the elements in the collection is from left to right and hence the name scanLeft.
  * Similar to the foldLeft method, scanLeft allows you to also specify an initial value.
  *
  * - def scanLeft[B, That](z: B)(op: (B, A) ⇒ B)(implicit bf: CanBuildFrom[Repr, B, That]): That
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-scanleft-example/
  */

object ScanLeftFunc extends App {

  val bag = Seq[Int](1, 2, 3, 4, 5)

  /*
    Scan method iterations
      0 + 1             =  1
      1 + 2             =  3
      1 + 2 + 3         =  6
      1 + 2 + 3 + 4     = 10
      1 + 2 + 3 + 4 + 5 = 15
   */
  println(bag.scanLeft(0)(_ + _)) // List(0, 1, 3, 6, 10, 15)

}

/**
  * The scanRight method takes an associative binary operator function as parameter and will use it to collapse elements from the collection to create a running total.
  * The order for traversing the elements in the collection is from right to left and hence the name scanRight.
  * Similar to the foldRight method, scanRight allows you to also specify an initial value.
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-scanright-example/
  */
object ScanRightFunc extends App {

  val bag = Seq[Int](1, 2, 3, 4, 5)

  /*
    scanRight method iterations
      5 + 4 + 3 + 2 + 1 = 15
      5 + 4 + 3 + 2     = 14
      5 + 4 + 3         = 12
      5 + 4             =  9
      5 + 0             =  5
      0                 =  0
   */
  println(bag.scanRight(0)(_ + _)) // List(15, 14, 12, 9, 5, 0)

}
