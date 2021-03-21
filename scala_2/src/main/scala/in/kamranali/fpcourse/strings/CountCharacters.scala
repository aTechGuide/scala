package in.kamranali.fpcourse.strings

// Given string, return the number of occurrences of each Char
object CountCharacters extends App {

  def countCharacters(s: String): Map[Char, Int] =
    s.foldLeft(Map.empty[Char, Int])((accum, value) => {
      val newCount = accum.getOrElse(value, 0) + 1
      accum + (value -> newCount )
    })

  println(countCharacters("scala"))
}
