package in.kamranali.fp.FuncExamples

/**
  *
  * The diff method takes another Set as its parameter and uses it to find the elements that are different from the current Set compared to another Set.
  *
  * - def diff(that: GenSet[A]): This
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-diff-function/
  */
object DiffFunc extends App {

  val bag = Set("kamran", "ali", "nitb", "guna")
  val place = Set("guna")

  println(bag diff place) // Set(kamran, ali, nitb)

  println(bag -- place) // Set(kamran, ali, nitb)


}
