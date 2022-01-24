package in.kamranali.algorithms.apis

import scala.collection.mutable

/*

  TreeSet
  - A mutable sorted set implemented using a mutable red-black tree as underlying data structure.
  - It is present in both mutable and immutable package


Good API Tut
- http://allaboutscala.com/tutorials/chapter-7-beginner-tutorial-using-scala-mutable-collection/scala-tutorial-learn-use-mutable-sortedset/
*/
object ScalaSortedSetAPIs extends App {

  // Ordering
  val ssetAsc = mutable.TreeSet[String]("a", "c", "b")
  println(ssetAsc) // TreeSet(a, b, c)

  val ssetDsc = mutable.TreeSet[String]("a", "c", "b")(Ordering.fromLessThan[String]((a, b) => b < a))
  println(ssetDsc) // TreeSet(c, b, a)

  // APIs
  ssetAsc.add("d") // +=
  ssetAsc.remove("b") // -=


}
