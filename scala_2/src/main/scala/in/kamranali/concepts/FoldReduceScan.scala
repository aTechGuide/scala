package in.kamranali.concepts

/**
  * Resource
  * - https://www.geeksforgeeks.org/scala-reduce-fold-or-scan/
  * - https://coderwall.com/p/4l73-a/scala-fold-foldleft-and-foldright
  */
object FoldReduceScan extends App {

  val data = List(1,2,3)

  /**
    * Reduce
    * - Parameter in the reduce function is a binary operation
    *   which merges all the elements from the collection and returns a single value
    */

  data.reduce(_ max _)

  /**
    * Fold
    * - fold also takes a binary operation which merges all the elements from the collection and returns a single value.
    *   The difference is that fold allows us to define an initial value.
    *   Due to this property, fold can also manage empty collections.
    *
    * - If the collection is empty, the value initialized becomes the final answer.
    * - Due to this we can also return a different value from the set of collection using initial value of some other datatype.
    *
    * - Reduce can only return the value of the same type because its initial value is the first value from the collection
    */

  data.fold(0)(_ + _)

  /**
    * Scan
    * - Scan function takes the binary operation as parameter and returns the value for each element in collection for that operation.
    *   It returns each iteration for that binary operator in the collection.
    *
    * - In scan also we can define the initial value.
    *
    */

  val resScan = data.scan(0)(_ + _) // List(0, 1, 3, 6)
  println(resScan)



}
