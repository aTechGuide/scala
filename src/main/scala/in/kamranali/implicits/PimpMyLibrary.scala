package in.kamranali.implicits

object PimpMyLibrary extends App {

  /*
    Enrichment allow us to decorate an existing classes (that we may NOT have access to) with additional methods and properties.

    There is a way to do this by

    IMPLICIT CLASSES
   */

  implicit class RichInt(value: Int) { //<- They take one and ONLY argument
    def isEven: Boolean = value % 2 == 0
    def sqrt: Double = Math.sqrt(value)
  }

  // What we can easily say is
  new RichInt(42).sqrt

  // But since its an implicit class wrapping INT we can also say

  42.isEven // <- compiler writes it as `new RichInt(42).isEven`

  // Above technique is called TYPE ENRICHMENT Or Pimping

  // For memory optimization purpose we can write  RichInt as

//  implicit class RichIntOptimized(val value: Int) extends AnyVal { //<- They take one and ONLY argument
//    def isEven: Boolean = value % 2 == 0
//    def sqrt: Double = Math.sqrt(value)
//  }

  /*
    Commented `RichIntOptimized` as line 23 will not compile
   */

  // Point 2
  // Compiler doesn't do multiple implicit searches e.g.

  implicit class RichBigInt(val value: BigInt) extends AnyVal {
    def isEven: Boolean = value % 2 == 0

  }

  implicit class RicherBigInt(richBigInt: RichBigInt) {
    def isOdd: Boolean = richBigInt.value % 2 != 0
  }

  32.isEven // <- Compile
  // 32.isOdd // Will NOT Compile

  // Because compiler wraps the 32 into whatever conversions it can access. BUT it doesn't do another search to enrich those as well.
  // i.e it can wrap `32` in `RichBigInt` but then it will not wrape `RichBigInt` to `RicherBigInt` in another step

  /*
    Exercise
   */
  // Enriching String Class

  implicit class RichString(string: String) {
    def asInt: Int = Integer.valueOf(string)

    def encrypt(cypherDistance: Int): String = string.map(c => (c + cypherDistance).asInstanceOf[Char])
  }

  println("3".asInt + 4)
  println("John".encrypt(2))

  /*
    Implicit Methods
   */

  // can we do "3" / 4
  // YES by using implicit methods

  implicit def stringToInt(string: String): Int = Integer.valueOf(string)

  println("6" / 2) //<- Re written as stringToInt("6") / 2
  // Compiler looks for all implicit classes, wrappers, conversions that given a string will return something that has `/` method

  // Example 2
  class RichAltInt(value: Int)
  implicit def enrich(value: Int): RichAltInt = new RichAltInt(value)

  // above method is equivalent to `implicit class RichAltInt(value: Int)`

  // Example 2 i.e. implicit conversion through Method is discouraged because if we have a bug its super hard to debug
  // Let' say we have
  implicit def intToBoolean(i: Int): Boolean = i == 1

  val aConditionedValue = if (3) "OK" else "Something Wrong"
  println(aConditionedValue)





}
