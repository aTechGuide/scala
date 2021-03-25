package in.kamranali.fpcourse.strings

/*
  /*
    "Alice loves Scala" => "Scala loves Alice"
    "    hello      world    " => "world hello"
   */
 */
object ReverseWords extends App {

  def reverseWords(string: String): String = {
    string.split(" ").filter(_.nonEmpty).reverse.mkString(" ")
  }

  println(reverseWords("a good   example"))

}
