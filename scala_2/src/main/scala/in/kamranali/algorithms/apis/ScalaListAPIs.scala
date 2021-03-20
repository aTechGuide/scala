package in.kamranali.algorithms.apis

/**
  * Notes
  * - List has O(1) prepend and head/tail access.
  * - Most other operations are O(n) on the number of elements in the list.
  *   - This includes the index-based lookup of elements, length, append and reverse.
  *
  * Reference
  * - https://alvinalexander.com/scala/how-create-scala-list-range-fill-tabulate-constructors/
  * - https://alvinalexander.com/scala/scala-list-class-examples/
  * - https://alvinalexander.com/scala/list-class-methods-examples-syntax/
  */

object ScalaListAPIs extends App {

  // Create List
  var data: List[Int] = List(1, 2, 3) // Java Style List.empty[Int]
  data = 1 :: 2 :: Nil // Lisp-style, We need to end the list with the `Nil` object
  data = List.fill[Int](3)(1) // List(1,1,1)
  data = List.tabulate[Int](5)(n => n * n) // Square of indices List(0, 1, 4, 9, 16)
  data = "123".toList.map(_.toInt)
  data = List.range(1,4) // List(1, 2, 3)

  // Operations

  /// Prepend
  data = 0 :: data // List(0, 1, 2, 3), O(1)

  data = List(0, 1) ::: List(2, 3) // List(0, 1, 2, 3) O(n), where n is the number of elements in the first List.

  /// Merge
  data = List.concat(List(0, 1), List(2, 3)) // List(0, 1, 2, 3)

  /**
    * When we work with a List we should think of it in terms of these operations:
    * - ::
    * - head
    * - tail
    *
    * Other methods you can use on List (that may be slow)
    * - :+ (append 1 item)
    * - ++ (append N items)
    * - +: (prepend 1 items)
    * - ++:(prepend N items)
    *
    * Remember -> : character is always next to the old (original) sequence. Also : is right-associative
    */

  println(data)

}
