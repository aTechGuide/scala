package in.kamranali.fpcourse.strings

import scala.annotation.tailrec

// Build Ransom Note from letters of a Magazine
object RansomNote extends App {

  /*
     ransomNote(
      "I have your daughter. I want 1000000 dollars, or you'll never see her again.",
      "I bought this really nice doll for my daughter. It was 20 dollars on Amazon. She's never been happier.
       I often have discounts from my network, so if you want to buy some really cool stuff for your kids, I can send you an invite
       if you sign up to my newsletter. It's read by 100000 people, and you'll never need to search for online discounts again."
     )
   */
  def ransomNote(ransomNote: String, magazine: String): Boolean = {

    def buildMap(string: String): Map[Char, Int] =
      string.groupBy(c => c).view.mapValues(_.length).toMap

    val noteChars = buildMap(ransomNote)
    val magChars = buildMap(magazine)

    noteChars.keySet
      .forall(char => magChars.getOrElse(char, 0) >= noteChars(char))
  }

  assert(ransomNote("I have your daughter. I want 1000000 dollars, or you'll never see her again.",
  "I bought this really nice doll for my daughter. It was 20 dollars on Amazon. She's never been happier.\n" +
    "I often have discounts from my network, so if you want to buy some really cool stuff for your kids, I can send you an invite\n" +
    "if you sign up to my newsletter. It's read by 100000 people, and you'll never need to search for online discounts again."))

}
