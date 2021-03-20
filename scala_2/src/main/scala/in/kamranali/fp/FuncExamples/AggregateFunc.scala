package in.kamranali.fp.FuncExamples

/**
  *
  * The aggregate method aggregates results by first applying a sequence operation (accumulator function) which is its first parameter
  * and then uses a combine operator to combine the results produced by the sequence operation.
  *
  * - def aggregate[B](z: =>B)(seqop: (B, A) => B, combop: (B, B) => B): B = foldLeft(z)(seqop)
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-aggregate-function/
  */
object AggregateFunc extends App {

  val bag = Seq("kamran", "ali")

  val agg = bag.aggregate[Int](0)((length, word) => length + word.length, (a, b) => a + b)

  println(agg) // 9

}
