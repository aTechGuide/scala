package in.kamranali.collections

object TuplesAndMaps extends App {

  /*
  - Tuples are finite ordered "lists"
  - Can group at most 22 elements of different types (we have 22 Function Types)
   */

  val aTuple1 = new Tuple2(2, "hello, Scala") // Tuple2[Int, String]
  val aTuple2 = Tuple2(2, "hello, Scala") // Using companion object's, apply method
  val aTuple3 = (2, "hello, Scala") // Syntactic Sugar

  println(aTuple1._1) // 2
  println(aTuple1.copy(_2 = "Good Bye Java"))
  println(aTuple1.swap) // ("hello, Scala", 2)

  /*
  Maps
  - Associate Keys to Values
   */

  val aMap: Map[String, Int] = Map()

  val phoneBook = Map(("Jim", 555), ("Daniel", 789), "Ali" -> 999).withDefaultValue(-1)

  println(phoneBook)

  println(phoneBook.contains("Jim"))
  println(phoneBook("Jim")) // If key is not found it returns an Exception

  val newPair = "Marry" -> 666
  val newPhoneBook = phoneBook + newPair

  println(newPhoneBook)

  // Functions  on maps: map, filter, flatMap

  println(phoneBook.map(pair => pair._1.toLowerCase -> pair._2))

  // filterKeys
  println(phoneBook.filterKeys(x => x.startsWith("J")))

  // mapValues
  println(phoneBook.mapValues(number => number * 10))


  // CONVERTIONS
  println(phoneBook.toList)
  println(List(("Daniel", 555)).toMap)

  val names = List("Bob", "James", "Kamran", "Jim")
  println(names.groupBy(name => name.charAt(0)))

}
