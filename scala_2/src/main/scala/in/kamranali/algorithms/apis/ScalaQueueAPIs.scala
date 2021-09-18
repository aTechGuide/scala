package in.kamranali.algorithms.apis

import scala.collection.immutable.Queue

object ScalaQueueAPIs extends App {

  var bag = Queue.empty[Int]

  // Enqueue
  bag = bag.enqueue(1)
  bag = bag.enqueue(2)
  bag = bag.enqueue(3)

  println(bag)

  // front Element
  println(s"Front = ${bag.front}")

  // Dequeue

  var data = bag.head
  bag = bag.tail

  println(s"bag = $bag and data = $data")

  val (data1, bag1) = bag.dequeue

  println(s"bag = $bag1 and data = $data1")
}
