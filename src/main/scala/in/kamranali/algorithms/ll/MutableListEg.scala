package in.kamranali.algorithms.ll

import scala.collection.mutable.ListBuffer

/**
  * - Constant time prepend and append
  * - Don’t use ListBuffer if you want to access elements arbitrarily, such as list(10000) use ArrayBuffer instead.
  */

object MutableListEg extends App {

  val listEg = ListBuffer[String]("Kamran", "Ali")

  // Adding Elements

  listEg += "Nit"
  listEg += ("Guna", "MP")

  listEg.foreach(println)

  // Remove
  listEg -= "Nit"
  listEg --= Seq("Guna", "MP")

  println("Removing Elements")
  listEg.foreach(println)



}
