package in.kamranali.fp.FuncExamples

/**
  * The reduce method takes an associative binary operator function as parameter and will use it to collapse elements from the collection.
  *
  * Unlike the fold method, reduce does not allow you to also specify an initial value.
  *
  * Calling the reduce method on an empty collection will throw an exception.
  * To avoid an exception being thrown, we can make use of the convenient reduceOption method instead
  *
  * - def reduce[A1 >: A](op: (A1, A1) ⇒ A1): A1
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-reduce-example/
  */

object ReduceFunc extends App {

  val prices = Seq(1.1, 2.0, 3.4)

  println( prices.reduce((acc, value) => acc + value)) // 6.5
  println( prices.reduce((acc, value) => acc min value)) // 1.1
  println( prices.reduce((acc, value) => acc max value)) // 3.4

  val flowers = Seq[String]("Rose", "Daisy", "lotus")
  println(flowers.reduce((acc, value) => acc +  ", " + value)) // Rose, Daisy, lotus
}

/**
  * The reduceLeft method takes an associative binary operator function as parameter and will use it to collapse elements from the collection.
  * The order for traversing the elements in the collection is from left to right and hence the name reduceLeft.
  * Unlike the foldLeft method, reduceLeft does not allow you to also specify an initial value.
  *
  * - def reduceLeft[B >: A](op: (B, A) ⇒ B): B
  *   the result of inserting `op` between consecutive elements of this $coll, going left to right:
  *   op( op( ... op(x_1, x_2) ..., x_{n-1}), x_n) where `x,,1,,, ..., x,,n,,` are the elements of this $coll.
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-reduceleft-example/
  */
object ReduceLeftFunc extends App {

  val prices = Seq(1.1, 2.0, 3.4)

  println( prices.reduceLeft((acc, value) => acc + value)) // 6.5

  val flowers = Seq[String]("Rose", "Daisy", "lotus")
  println(flowers.reduceLeft((acc, value) => acc +  ", " + value)) // Rose, Daisy, lotus
}

/**
  * The reduceRight method takes an associative binary operator function as parameter and will use it to collapse elements from the collection.
  * The order for traversing the elements in the collection is from right to left and hence the name reduceRight.
  * Unlike the foldRight method, reduceRight does not allow you to also specify an initial value.
  *
  *  - def reduceRight[B >: A](op: (B, A) ⇒ B): B
  *    the result of inserting op between consecutive elements of this collection or iterator, going right to left:
  *    op(x_1, op(x_2, ..., op(x_{n-1}, x_n)...)) where x,,1,,, ..., x,,n,, are the elements of this collection or iterator.
  *
  *  The reduceRight method is a member of the TraversableOnce trait.
  *  You should bear in mind that using reduceRight comes with an overhead with elements from the collection being initially reversed
  *  as shown in it's implementation from the Scala source code of TraversableOnce trait.
  *
  *  Prefer to use reduceLeft.
  * def reduceRight[B >: A](op: (A, B) => B): B = {
  *   if (isEmpty)
  *     throw new UnsupportedOperationException("empty.reduceRight")
  *
  *     reversed.reduceLeft[B]((x, y) => op(y, x))
  * }
  *
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-reduceright-example/
  */

object ReduceRightFunc extends App {

  val prices = Seq(1.1, 2.0, 3.4)

  println( prices.reduceRight((acc, value) => acc + value)) // 6.5

  val flowers = Seq[String]("Rose", "Daisy", "lotus")
  println(flowers.reduceRight((value, acc) => value +  ", " + acc)) // Rose, Daisy, lotus
}
