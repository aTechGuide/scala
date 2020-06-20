package in.kamranali.algorithms.apis

/**
  * Important Docs and Points
  *
  * References
  * - https://alvinalexander.com/scala/scala-immutable-map-class-methods-examples-syntax/
  */

object ScalaMapAPIs extends App {

  /**
    * Immutable Map APIs
    */

  // Create
  var data: Map[String, Int] = Map[String, Int]("kamran" -> 1, "ali" -> 2) // Map[String, Int]()

  // Add [+, ++] Provide key-value pair
  data = data + ("guna" -> 3) // data += ("guna" -> 3)
  data = data ++ Map("mp" -> 4)
  data = data ++ List("a" -> 99, "b" -> 98)

  // Remove [-, --] Provide keys to remove
  data = data - "guna"
  data = data -- Seq("mp")
  data = data -- Array("a", "b")

  // Access Elements
  // println(data("ali1")) // <- Throws NoSuchElementException. DO NOT use this

  println(data getOrElse("ali", "no value found")) // 2
  println(data getOrElse("ali1", "no value found")) // "no value found"

  data get "ali" match { case Some(value) => value case None =>} // 2

  // Check for element
  println(data contains "ali") // true

  // Update Elements
  data =  data.updated("ali", 3) // Internally data + ("ali" -> 3) Final Map [Map(ali -> 3, kamran -> 1)]

  // Iterate
  data foreach {tuple => tuple._1 + tuple._2}

  data.keys.foreach {key => key}  // data.keySet foreach {key => key}
  data.values.foreach {value => value} // data.valuesIterator.contains(2)



  println(data)







}
