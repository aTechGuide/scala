package in.kamranali.OOP

object PackagingAndImprots extends App {

  /*
  Package
  1. Bunch of definitions Grouped under same name e.g. ` package in.kamranali.OOP `
  2. Not an expression
  3. Package members are accessible by Simple Name
  4. Import the package Or use Fully Qualified Class Name; If not in proper package
  5. Are in Hierarchy
  6. Package Object
  7. Packages allows us to do aliasing
    a. e.g. import java.sql.{Date => SqlDate}
  8. Default imports are packages that are imported without intentional import from us
    a. e.g. java.lang - String, Exception ...
    b. e.g. scala - Int, Nothing, Function
    c. e.g. scala.Predef - println, ???
   */

  val writer = new Writer("Kamran", "Ali", 2018) // Package members are accessible by Simple Name

  // Package Object
  sayHello
  println(SPEED_OF_LIGHT)
}
