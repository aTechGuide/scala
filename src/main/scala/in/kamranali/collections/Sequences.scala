package in.kamranali.collections

import scala.util.Random

object Sequences extends App {

  /**
    - They have well defined order
    - They can be indexed
   */

  val aSequence = Seq(1,3,2,4) // Seq is companion object whose `apply` has a factory method that constructs subclasses of Seq

  println(aSequence)

  println(aSequence.reverse)
  println(aSequence(2)) // calls apply
  println(aSequence ++ Seq(5,7,6))
  println(aSequence.sorted)

  // Ranges
  val aRange: Seq[Int] = 1 to 10  // 1 until 10
  aRange.foreach(println)

  // List
  val aList = List(1,2,3)
  val prepended = 42 :: aList // Or :+
  println(prepended)

  val apples5 = List.fill(5)("apple") // fill is a curried function
  println(apples5)

  println(aList.mkString("-"))

  // Arrays (Equivalent to Java Arrays)

  val numbers = Array(1,2,3,4)

  val threeElements = Array.ofDim[Int](3) // allocates array for 3 elements
  threeElements.foreach(println) // array is initialised with '0' value

  numbers(2) = 0 // syntax sugar for `numbers.update(2,0)`. So `update` is also special method in scala
  println(numbers.mkString(" "))

  val numberSeq: Seq[Int] = numbers // `=` is implicit conversion
  println(numberSeq)

  // Vectors: Default implementation of Immutable Seq
  // Constant Read and write O(logn) to base 32

  val vector = Vector(1,2,3)
  println(vector)

  /**
   * vectors vs lists
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

  // Vector traverser whole 32 branch tree and replace that entire chunk of 32-elements
  // Dept of tree is small
  val numbersVector = (1 to maxCapacity).toVector

  println(getWriteTime(numberList))
  println(getWriteTime(numbersVector))

  // Vector is more efficient than List

}
