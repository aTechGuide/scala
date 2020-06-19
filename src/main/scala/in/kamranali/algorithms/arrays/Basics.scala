package in.kamranali.algorithms.arrays

import scala.collection.mutable.ArrayBuffer

object Basics extends App {


  val array = ArrayBuffer[String]("Kamran", "Ali")

  array += "NIT"
  array += "Guna"

  array.foreach(println)

  println()
  println(s"Oth element is ${array(0)}")

  // Removing elements

  array -= "Ali"
  array.remove(0)

  println("Removed Ali and 0th position")
  array.foreach(println)




}
