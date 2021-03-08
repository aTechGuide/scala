package in.kamranali.fp.FuncExamples

/**
  * The fold method takes an associative binary operator function as parameter and will use it to collapse elements from the collection.
  *
  * The fold method allows you to also specify an initial value, which is a neutral element for the fold operation;
  * may be added to the result an arbitrary number of times, and must not change the result
  * (e.g., `Nil` for list concatenation, 0 for addition, or 1 for multiplication).
  *
  * - def fold[A1 >: A](z: A1)(op: (A1, A1) ⇒ A1): A1
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-fold-example/
  */
object FoldFunc extends App {

  val prices = Seq(1.1, 2.0, 3.4)
  val items = Seq[Int](1, 2, 3)

  println( prices.fold(0.0)(_ + _)) // 6.5
  // println(items.fold[String]("")((accum, item) => ))

}

/**
  * The foldLeft method takes an associative binary operator function as parameter and will use it to collapse elements from the collection.
  *
  * The order for traversing the elements in the collection is from left to right and hence the name foldLeft.
  *
  * The foldLeft method allows you to also specify an initial value.
  *
  * Using foldLeft is fundamental in recursive function and will help you prevent stack-overflow exceptions.
  *
  * - def foldLeft[B](z: B)(op: (B, A) ⇒ B): B
  *   - the result of inserting op between consecutive elements of this collection or iterator, going left to right with the start value z on the left: op(...op(z, x_1), x_2, ..., x_n)
  *
  *   - where x,,1,,, ..., x,,n,, are the elements of this collection or iterator. Returns z if this collection or iterator is empty.
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-foldleft-example/
  */

object FoldLeftFunc extends App {

  val items = Seq[Int](1, 2, 3)
  val flowers = Seq[String]("Rose", "Daisy", "lotus")

  println( items.foldLeft[Int](100)(_ + _)) // Different Seed value
  println( items.foldLeft[Double](0.0)(_ + _)) // 6.0 [Different Type]
  println(flowers.foldLeft[String]("")((acc, flower) => acc + flower + " flower ")) // Rose flower Daisy flower lotus flower

}

/**
  * The foldRight method takes an associative binary operator function as parameter and will use it to collapse elements from the collection.
  *
  * The order for traversing the elements in the collection is from right to left and hence the name foldRight.
  *
  * The foldRight method allows you to also specify an initial value.
  *
  * Prefer using foldLeft as opposed to foldRight since foldLeft is fundamental in recursive function and will help you prevent stack-overflow exceptions.
  *
  * - def foldRight[B](z: B)(op: (A, B) ⇒ B): B
  *   the result of inserting op between consecutive elements of this collection or iterator, going right to left with the start value z on the right: op(x_1, op(x_2, ... op(x_n, z)...))
  *   where x,,1,,, ..., x,,n,, are the elements of this collection or iterator. Returns z if this collection or iterator is empty
  *
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-foldright-example/
  */
object FoldRightFunc extends App {

  val items = Seq[Int](1, 2, 3)
  val flowers = Seq[String]("Rose", "Daisy", "lotus")

  println( items.foldRight[Double](0.0)(_ + _)) // 6.0
  println(flowers.foldRight[String]("")((flower, acc) => flower + " flower " + acc)) // Rose flower Daisy flower lotus flower

}
