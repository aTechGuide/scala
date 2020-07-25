package in.kamranali.algorithms.apis

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
  * Array
  * - Creates an Array of fixed Size
  * - A wrapper around the Java array primitive type
  * - While writing multi-threaded or FP code, we won’t want to use it because it’s elements can be mutated.
  *   You should generally prefer the Scala Vector and ArrayBuffer classes rather than using Array
  *
  *
  * Vector
  * - Fast, Indexed, immutable, sequential collection
  * - Make the Scala Vector Class Your ‘Go To’ Immutable Sequence for general purpose use
  * - [Scala Doc] Vector is a collection type (introduced in Scala 2.8) that addresses the inefficiency for random access on lists.
  *   Vectors allow accessing any element of the list in ‘effectively’ constant time ...
  *
  *   Because vectors strike a good balance between fast random selections and fast random functional updates,
  *   they are currently the default implementation of immutable indexed sequences
  *
  * ArrayBuffer
  * - Make the ArrayBuffer class your default Mutable indexed sequence
  * - [Scala Doc] Append, update, and random access take constant time (amortized time). Prepends and removes are linear in the buffer size.
  *   - Efficiently in building up a large collection whenever the new items are always added to the end
  *
  * ListBuffer
  * - Mutable sequential collection that works more like a List
  * - [Scala Doc] A ListBuffer is like an Array buffer except that it uses a linked list internally instead of an array.
  *   If you plan to convert the buffer to a list once it is built up, use a list buffer instead of an array buffer.
  *
  *   A Buffer implementation backed by a list. It provides constant time prepend and append. Most other operations are linear.
  *
  * - Linear sequence rather than an indexed sequence — use ListBuffer instead of ArrayBuffer
  *
  * Performance Comparison
  * - https://docs.scala-lang.org/overviews/collections/performance-characteristics.html
  *
  * Reference
  * - https://alvinalexander.com/scala/array-class-methods-examples-syntax/
  * - https://alvinalexander.com/scala/make-vector-class-default-immutable-sequence-scala-cookbook/
  * - https://alvinalexander.com/scala/make-arraybuffer-class-default-mutable-indexed-sequence-scala-cookbook/
  * - https://www.scala-lang.org/api/2.12.x/scala/collection/mutable/ListBuffer.html
  */
object ScalaArrayAPIs extends App {

  def TestRecursive(incr: Int, even: ArrayBuffer[Int], result: ArrayBuffer[Int]): Unit = {

    if (incr == 3 ) {
      result += 3
    }
    else {
      if (incr % 2 == 0) even += incr
      TestRecursive(incr + 1, even, result)
    }
  }

  val res = ArrayBuffer[Int]()
  val even = ArrayBuffer[Int]()
  println(res)

  TestRecursive(0, even, res)

  println(res)
  println(even)

  val array = res.toArray[Int]
  val listB = ListBuffer

}
