package in.kamranali.fpcourse.strings

// Anagram -> Two Strings Containing Same Letters
object AnagramCheck extends App {

  import CountCharacters._

  def checkAnagrams(sa: String, sb: String): Boolean = {
    val charA = countCharacters(sa)
    val charB = countCharacters(sb)

    charA.foldLeft(true)((accum, value) => {
      val (key, count) = value

      if (charB.contains(key) && charB(key) == count) accum
      else false
    })
  }

  def checkAnagramsShort(sa: String, sb: String): Boolean =
    countCharacters(sa) == countCharacters(sb)

  def checkAnagramsShort2(sa: String, sb: String): Boolean =
    sa.sorted == sb.sorted

  assert(checkAnagrams("desserts", "stressed"))
  assert(!checkAnagrams("Scala", "Haskell"))

}
