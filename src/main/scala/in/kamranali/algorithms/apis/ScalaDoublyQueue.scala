package in.kamranali.algorithms.apis

import scala.collection.mutable

object ScalaDoublyQueue extends App {

  val dq = mutable.ArrayDeque[Int](1)

  dq.append(2)
  dq.prepend(3)

  println(dq) // ArrayDeque(3, 1, 2)
  println(dq.head) // 3
  println(dq.last) // 2

  println(dq.removeHead()) // 3
  println(dq) // ArrayDeque(1, 2)

  println(dq.removeLast()) // 2
  println(dq) // ArrayDeque(1)

}
