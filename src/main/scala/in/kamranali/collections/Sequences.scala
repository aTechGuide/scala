package in.kamranali.collections

import scala.util.Random

/**
  * Basic Scala Lesson 30 [Sequences]
  *
  * In Notes of One Note
  * - Scala > Concepts
  *
  *
  * Ref
  * - https://www.udemy.com/course/rock-the-jvm-scala-for-beginners/learn/lecture/7660720
  */

object Sequences extends App {

  /**
    Seq:
    A very general interface for data structures that
    - have well defined order
    -  can be indexed
   */

  val aSequence: Seq[Int] = Seq(1,2,3,4) // Seq is companion object whose `apply` has a factory method that constructs subclasses of Seq

  println(aSequence) // List(1, 2, 3, 4)

  println(aSequence.reverse) // List(4, 3, 2, 1)
  println(aSequence(2)) // calls apply; 3 is printed
  println(aSequence ++ Seq(5, 6, 7)) // List(1, 2, 3, 4, 5, 6, 7)
  println(aSequence.sorted) // List(1, 2, 3, 4)

  /**
    * Ranges
    */
  val aRange: Seq[Int] = 1 to 10  // 1 until 10
  aRange.foreach(println) // prints 1 2 3 4 ... 10

  /**
    * List
    */
  val aList = List(1,2,3)
  val prepended = 42 :: aList // Or +:
  val appended = aList :+ 42
  println(prepended) // List(42, 1, 2, 3)
  println(appended) // List(1, 2, 3, 42) // To remember ":" is one the side of list

  val apples5 = List.fill(5)("apple") // fill is a curried function
  println(apples5) // List(apple, apple, apple, apple, apple)

  println(aList.mkString("-")) // 1-2-3

  /**
    * Arrays (Equivalent to Java Arrays)
    */

  val numbers = Array(1,2,3,4)

  val threeElements = Array.ofDim[Int](3) // allocates array for 3 elements
  threeElements.foreach(println) // array is initialised with '0' value

  // mutation
  numbers(2) = 0 // syntax sugar for `numbers.update(2,0)`. So `update` is also special method in scala
  println(numbers.mkString(" ")) // 1 2 0 4

  // How Array is a Sequence?
  val numberSeq: Seq[Int] = numbers // `=` is an implicit conversion
  println(numberSeq) // WrappedArray(1, 2, 0, 4)

  /**
    * Vectors
    * - Default implementation of Immutable Seq
    * - Constant Read and write O(logn) to base 32
    */

  val vector = Vector(1,2,3)
  println(vector) // Vector(1, 2, 3)

  /**
   * vectors vs lists [Performance Test]
   */

  val maxRuns = 1000
  val maxCapacity = 1000000

  def getWriteTime(collection: Seq[Int]): Double = {
    val r = new Random

    val times = for {
      it <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime

      collection.updated(r.nextInt(maxCapacity), 0)
      System.nanoTime() - currentTime
    }

    (times.sum  * 1.0) / maxRuns
  }

  // List saves the reference to the tail
  // Updating first element -> it is efficient
  // Updating middle element -> Not so efficient
  val numberList = (1 to maxCapacity).toList

  // Vector traverse whole 32 branch tree and replace that entire chunk of 32-elements
  // Depth of tree is small
  val numbersVector = (1 to maxCapacity).toVector

  println(getWriteTime(numberList))
  println(getWriteTime(numbersVector))

  // Vector is more efficient than List and is the default implementation of Sequence

}
