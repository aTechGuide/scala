package in.kamranali.algorithms.apis

import scala.collection.immutable.Queue
import scala.collection.mutable

object StackQueueCompare extends App {

  /* Queue*/
  // FIFO
  // Adding items to the queue always has cost O(1). Removing items has cost O(1), except in the case where a pivot is required,
  var q = Queue(1, 2, 3, 4).enqueue(5)

  /* Stack */
  // LIFO [Open the docs of library for more details]
  // List has O(1) prepend and head/tail access. Most other operations are O(n)
  var l = 5 :: List(1, 2, 3, 4)

  //println(s"q head ${q.head}  front ${q.front} tail ${q.tail}")
  //println(s"l head ${l.head} tail ${l.tail}")

  /* DQueue */
  // Append, prepend, removeFirst, removeLast and random-access (indexed-lookup and indexed-replacement) take amortized constant time
  // https://www.scala-lang.org/api/2.13.6/scala/collection/mutable/ArrayDeque.html
  var dq = mutable.ArrayDeque[Int](1)
  dq.append(2) // add to end
  dq.prepend(3) // add to first

  println(s"Original $dq")
  println(s"Access first ${dq.head} and last ${dq.last}")  // Peek APIs
  println(s"Removed from first ${dq.remove(0)} remaining $dq") // Front Remove API
  println(s"Removed from last ${dq.removeLast()} remaining $dq") // End Remove API



}
