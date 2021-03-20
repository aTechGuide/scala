package in.kamranali.collections

/**
  * Basic Scala Lesson 31 [Tuples And Maps]
  *
  * Ref
  * - https://www.udemy.com/course/rock-the-jvm-scala-for-beginners/learn/lecture/7660722#overview
  */

object TuplesAndMaps extends App {

  /**
  - Tuples are finite ordered "lists"
  - Can group at most 22 elements of different types (we have 22 Function Types)
   */

  val aTuple1: (Int, String) = new Tuple2(2, "hello, Scala") // Tuple2[Int, String]
  val aTuple2 = Tuple2(2, "hello, Scala") // Using companion object's, apply method
  val aTuple3 = (2, "hello, Scala") // Syntactic Sugar

  println(aTuple1._1) // 2
  println(aTuple1.copy(_2 = "Good Bye Java")) // (2,Good Bye Java)
  println(aTuple1.swap) // (hello, Scala,2)

  /**
    Maps
    - Associate Keys to Values
   */

  val aMap: Map[String, Int] = Map()

  val phoneBook = Map(("Jim", 555), ("Daniel", 789), "Ali" -> 999).withDefaultValue(-1)
  // a -> b is sugar for (a, b)

  println(phoneBook)

  println(phoneBook.contains("Jim")) // true
  println(phoneBook("Jim")) // If key is not found it returns an Exception

  val newPair = "Marry" -> 666
  val newPhoneBook = phoneBook + newPair

  println(newPhoneBook) // Map(Jim -> 555, Daniel -> 789, Ali -> 999, Marry -> 666)

  // Functions on maps: map, filter, flatMap

  println(phoneBook.map(pair => pair._1.toLowerCase -> pair._2)) // transforming the map by lower casing the keys
  // PS: If by doing `pair._1.toLowerCase -> pair._2)` resulting keys overlap we will lose data

  // filterKeys
  println(phoneBook.filterKeys(_.startsWith("J"))) // Map(Jim -> 555)

  // mapValues
  println(phoneBook.mapValues(_ * 10)) // Map(Jim -> 5550, Daniel -> 7890, Ali -> 9990)


  // CONVERSIONS to other Collections
  println(phoneBook.toList) // List((Jim,555), (Daniel,789), (Ali,999))
  println(List(("Daniel", 555)).toMap) // Map(Daniel -> 555)

  val names = List("Bob", "James", "Kamran", "Jim")
  println(names.groupBy(_.charAt(0))) // Map(J -> List(James, Jim), K -> List(Kamran), B -> List(Bob))

}
