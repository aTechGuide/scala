package in.kamranali.algorithms.apis

import scala.collection._

/**
  *
  * Reference
  * - http://alvinalexander.com/scala/scala-set-class-how-to-add-elements-cookbook-recipes/
  * - https://alvinalexander.com/scala/how-to-use-sortable-sets-in-scala-sortedset-treeset/
  * - http://allaboutscala.com/tutorials/chapter-6-beginner-tutorial-using-scala-immutable-collection/scala-tutorial-learn-use-immutable-set/
  */
object ScalaSetAPIs extends App {

  // Create Set
  var data = Set[Int](1, 2, 3) // data = Set.empty[Int]

  // Add an element
  data += 4 // data = data + 4
  data += (5, 6)

  data ++= Set(7,8)

  // Remove an element
  data -= 4 // data = data - 4
  data -= (5, 6)
  data --= Set(7,8)

  // check element exists
  data.contains(1)

  // Operations
  /// Intersection
  println(data intersect Set(2)) // Or `data & Set(2)` prints -> Set(2)

  /// Intersection
  println(data diff Set(2)) // Or `data &~ Set(2)` prints -> Set(1, 3)

}
