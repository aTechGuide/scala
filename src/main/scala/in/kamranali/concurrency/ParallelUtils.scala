package in.kamranali.concurrency

/**
  * [Concurrency] Chapter 10: Parallel Collections
  *
  * Parallel Collections is a separate module in Scala 2.13
  * - https://github.com/scala/scala-parallel-collections
  */

import java.util.concurrent.ForkJoinPool

import scala.collection.parallel.ForkJoinTaskSupport
import scala.collection.parallel.immutable.ParVector

import scala.collection.parallel.CollectionConverters._ // for .par to work

object ParallelUtils extends App {

  /*
    Parallel Collections => Operations on them are handled by multiple threads at the same time
   */

  val parallelList = List(1,2,3).par

  val aParallelVector = ParVector[Int](1,2,3)

  /**
   * Parallel Collections
   *
   * - Seq
   * - Vectors
   * - Array
   * - Map -> Hash Map, Trie Map
   * - Set -> Hash Set, Trie Set
   */

  /*
    Parallel Collection Efficiency Test
   */
  def measure[T](operation: => T): Long = {
    val time = System.currentTimeMillis()
    operation
    System.currentTimeMillis() - time
  }

  val list = (1 to 1000).toList
  val serialTime = measure {
    list.map(_ + 1)
  }

  val parallelTime = measure {
    list.par.map(_ + 1)
  }

  println("Serial Time: " + serialTime + " Parallel Time: " + parallelTime)

  // For 10000000 => Serial Time: 9596 Parallel Time: 3002

  // For 1000 => Serial Time: 7 Parallel Time: 117

  // We got above results because

  /*
    Parallel Collections operate on

    MAP Reduce Model
      - split the elements into chunks (by Splitter)
      - operation
      - recombine (Reduce Step by combiner)
   */


  // CAVEAT 1
  // for Parallel Collections -> For non associative operators e.g. Fold, reduce we will get different results
  println(List(1,2,3).reduce(_ - _))
  println(List(1,2,3).par.reduce(_ - _))


  // CAVEAT 2
  // Synchronization:
  var sum = 0
  List(1,2,3).par.foreach(sum += _)
  println(sum) // 6 is not guaranteed because of side effects might be computed in different order
  // (Or there might be under race condition because sum might be accessed by simultaneous threads at same time)


  /**
    Configuring parallel collections
   */
  // Two threads managing aParallelVector
  aParallelVector.tasksupport = new ForkJoinTaskSupport(new ForkJoinPool(2))

  /*
    Alternatives of ForkJoinTaskSupport
    - ThreadPoolTaskSupport - deprecated
    - ExecutionContextTaskSupport
   */

}





