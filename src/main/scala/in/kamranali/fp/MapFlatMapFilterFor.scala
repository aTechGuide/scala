package in.kamranali.fp

object MapFlatMapFilterFor extends App {

  val list = List(1,2,3) // Creating a list by calling `apply` on List companion object

  // Map
  println(list.map(x => x + 1)) // List(2, 3, 4)
  println(list.map(_ + " is a number")) // List(1 is a number, 2 is a number, 3 is a number)

  // Filter
  println(list.filter(_ % 2 == 0)) // List(2)

  // Flat Map
  val toPair = (x: Int) => List(x, x + 1)
  println(list.flatMap(toPair)) // List(1, 2, 2, 3, 3, 4)

  /*
    Q: Print all combinations between two lists
   */
  val numbers = List(1,2,3,4)
  val chars = List('a', 'b', 'c', 'd')

  // print a1, a2, a3 ,... d4 Which is a functional way of iterating

  println(numbers.flatMap(x => chars.map( c => "" + c + x))) // List(a1, b1, c1, d1, a2, b2, c2, d2, a3, b3, c3, d3, a4, b4, c4, d4)

  // For each
  list.foreach(println) // Similar to map; Just that it receives function returning unit

  /*
    For - Comprehensions
    - Shorthand for Chains
   */

  // Let's write shorthand for `numbers.flatMap(x => chars.map( c => "" + c + x))`
  val forCombinations1 = for {
    x <- numbers
    c <- chars
  } yield "" + c + x

  println(forCombinations1) // List(a1, b1, c1, d1, a2, b2, c2, d2, a3, b3, c3, d3, a4, b4, c4, d4)

  val forCombinations2 = for {
    x <- numbers if x % 2 == 0 // keeping even numbers
    c <- chars
  } yield "" + c + x

  println(forCombinations2) // List(a2, b2, c2, d2, a4, b4, c4, d4)


  /*
    Syntax Overload
   */

  list.map { x =>
    x * 2
  }

}
