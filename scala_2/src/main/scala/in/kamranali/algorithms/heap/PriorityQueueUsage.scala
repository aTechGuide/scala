package in.kamranali.algorithms.heap

import scala.collection.mutable

object PriorityQueueUsage extends App {

  /**
    * Max Queue
    */
  // val maxQueue = mutable.PriorityQueue(1,7,5,3,2)(Ordering.fromLessThan((a, b) => a < b)) // 7

  val maxQueue = mutable.PriorityQueue(1,7,5,3,2) // 7

  println("** Max Queue **")
  println(maxQueue.dequeue) // 7
  println(maxQueue.dequeue) // 5

  println(maxQueue.head) // 3
  println(maxQueue.dequeue) // 3
  println(maxQueue.dequeue) // 2
  println()

  /**
    * Min Queue
    */

  val predicate: Ordering[Int] = Ordering.fromLessThan((a, b) => b < a)

  val minQueue = mutable.PriorityQueue(1,7,5,3,2)(predicate)

  println("** Min Queue **")
  println(minQueue.dequeue) // 1
  println(minQueue.dequeue) // 2
  println(minQueue.dequeue) // 3



}
