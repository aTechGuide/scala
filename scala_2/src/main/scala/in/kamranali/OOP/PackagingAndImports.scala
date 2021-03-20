package in.kamranali.OOP

/**
  * Basic Scala Lesson 23 a [Packaging And Imports]
  *
  * Ref
  * - https://www.udemy.com/course/rock-the-jvm-scala-for-beginners/learn/lecture/7660676
  */

object PackagingAndImports extends App {

  /*
  Package
  1. Bunch of definitions Grouped under same name e.g. ` package in.kamranali.OOP `
  2. Not an expression
  3. Package members are accessible by Simple Name
  4. Import the package Or use Fully Qualified Class Name; If not in proper package
  5. Are in Hierarchy [Hierarchy being given by dot (.) notation]
  6. Package Object [This originated from the problem that sometimes we may want to write methods or constants outside of basically everything else e.g. class, traits etc]
  7. Packages allows us to do aliasing
    a. e.g. import java.sql.{Date => SqlDate}
  8. Default imports are packages that are imported without intentional import from us
    a. e.g. java.lang - String, Object, Exception ...
    b. e.g. scala - Int, Nothing, Function
    c. e.g. scala.Predef - println, ???
   */

  val writer = new Writer("Kamran", "Ali", 2018) // Package members are accessible by Simple Name

  // Package Object
  sayHello
  println(SPEED_OF_LIGHT)
}
