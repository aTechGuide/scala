package in.kamranali.fp.FuncExamples

/**
  * The exists method takes a predicate function and will use it to find the first element in the collection which matches the predicate.
  *
  * - def exists(p: (A) ⇒ Boolean): Boolean
  *
  * Ref
  * - http://allaboutscala.com/tutorials/chapter-8-beginner-tutorial-using-scala-collection-functions/scala-exists-function/
  */
object existsFunc extends App {

  val bag = Seq("kamran", "ali", "nitb", "guna")
  println(bag exists (_.equalsIgnoreCase("Nitb"))) // true

  val predicate1 = (data: String) => data.equalsIgnoreCase("guna")
  println(bag exists predicate1) // true

  def predicate2(data: String) = data.equalsIgnoreCase("Ali")
  println(bag exists predicate2) // true


}
