package in.kamranali.fp

class FunctionalCollection {

  // If we look Set closely we can see

  val set = Set(1,2,3)

  // 1. Set instances are callable like functions. Its apply method always return true/false

  println(set(1))
  println(set(2))

  // 2. Set behave like actual functions OR SET ARE FUNCTIONS
  // trait Set[A] extends (A) => Boolean with ....


  /*
  Seq are partially defined on domain [0, ... , length - 1]
  Seq are partial functions
   */

  /*
  Similarly Map are partial function defined on domain of its keys
   */
}
