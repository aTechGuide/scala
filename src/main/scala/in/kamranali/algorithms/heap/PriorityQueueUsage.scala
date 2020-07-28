package in.kamranali.algorithms.heap

import scala.collection.mutable

object PriorityQueueUsage extends App {

  /**
    * Max Queue
    */
  val maxQueue = mutable.PriorityQueue(1,7,5,3,2) // 7
  println(maxQueue.dequeue()) // 7

  /**
    * Min Queue
    */

  val predicate: Ordering[Int] = (a: Int, b: Int) => b compare a

  val minQueue = mutable.PriorityQueue(1,7,5,3,2)(predicate)
  println(minQueue.dequeue()) // 1



}
