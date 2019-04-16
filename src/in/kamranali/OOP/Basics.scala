package in.kamranali.OOP

object Basics extends App {

  /*
  Basic Class
   */
  val cat = new Animal
  // println(cat)

  /*
  Class with Parameters
   */
  val kamran = new Person("Kamran", 26)
  //println(kamran)

  // kamran.age will not compile because age is a class parameter NOT a class member (OR Fields)

  /*
  Class with Member/Field
   */
  // In Person1 `age` is Field and can be accessed by dot(.) operator
  val palash = new Person1("Kamran", 26)
  //println(palash.age)

  /*
  Class with Body
   */

  val ali = new Person2("Kamran", 26) // Prints 4

  //println(ali.x) // Prints 2

  /*
  Class with Function/Method and Constructor
   */

  val mayank = new Person3("Kamran", 26) // Prints 4
  mayank.greet("Daniel")


}
// Bare minimum
class Animal

// Class with Parameters
class Person(name: String, age: Int) // <- Constructor

// Class with Member/Field
class Person1(name: String, val age: Int)

// Class with Body
class Person2(name: String, val age: Int) {

  // Body

  val x = 2 // <- `x` is field and is accessible outside

  println(4) // This value is printed at every instantiation of Class because the whole block of code is evaluated at object instantiation.
}


// Class with Function/Method and Constructor
class Person3(name: String, val age: Int) {

  // Method
  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")
  // whether name is field or not we can access it using `this`

  // Method Overloading
  def greet(): Unit = println(s"Hi, I'm ${name}") // here name is this.name

  // Multiple Constructor
  // Implementation of secondary constructor has to be another constructor and NOTHING ELSE
  def this(name: String) = this(name, 0) // we can avoid constructors by using default parameters for class parameters e.g. class Person3(name: String, val age: Int = 0) { ... }

  def this() = this("John Doe")

}

/*
Assignment
*/

class Writer(firstName: String, lastName: String, val year: Int) {

  def fullName(): String = firstName + " " + lastName
}

class Novel(name: String, year: Int, author: Writer) {

  def authorAge = year - author.year

  def isWrittenBy(author: Writer) = author == this.author

  def copy(newYear: Int): Novel = new Novel(name, newYear, author)
}

class Counter(val count : Int) {

  def inc = new Counter(count + 1) //  IMMUTABILITY -> Returning new counter in place of modifying current count i.e. ` count + 1 `

  // RULE: whenever we want to modify the content of an instance return new Instance.

  def dec = new Counter(count - 1)

  def inc(n: Int) = new Counter(count + n)
  def dec(n: Int) = new Counter(count - n)
}