package in.kamranali.implicits

/**
  * Advance Scala Lesson 35 [Type Classes]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/11053820
  */

object TypeClasses extends App {

  /**
      TypeClasses is a trait that takes a type and describes what operations can be applied to that type
      It basically describes the collection of properties or methods that a Type must have in order to belong to that specific type class
      e.g. If class belongs to an `Ordering` Type class then instances of that type have the ability to compare values and tell if one is less than other

      All implementers of Type class are called Type class instances
   */

  // Method to Render HTML
  trait HTMLWritable {
    def toHTML: String
  }

  case class User(name: String, age: Int, email: String) extends HTMLWritable {
    override def toHTML: String = s"<div> $name ($age yo) <a href=$email/> </div>"
  }

  User("John", 13, "johan@abc.con").toHTML // <- Correct

  /*
    Disadvantages of toHTML Method
    1 Works for the TYPES we write e.g. User Type. Can't be used for Java Standard Types
    2 ONE implementation out of quite a number e.g. we need to supply different implementation for logged In user etc.

    */

  // Option 2: Use Pattern Matching to Render HTML
  object HTMLSerializer {
      def serializeToHTML(value: Any): Unit = value match {
        case User(name, age, email) => // rendering user
        case _ =>
      }
    }

  /*
    Disadvantages of Option 2

    1. We LOST Type Safety
    2. We need to modify code every time, when ever we add a new Data structure / Something we want to render on page
    3. Still ONE implementation for each given Type e.g. we need to supply different implementation for logged In user etc.
   */

  // Option 3
  trait HTMLSerializer[T] { // <- TYPE Class with Type T
    def serialize(value: T): String
  }

  // Type class instances
  object UserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String = s"<div> ${user.name} (${user.age} yo) <a href=${user.email}/> </div>"
  }

  val john = User("John", 13, "johan@abc.con")
  println(UserSerializer.serialize(john)) // <div> John (13 yo) <a href=johan@abc.con/> </div>

  /*
    Advantages of Option 3
    1. We can define Serializers for other types (Even Types that we have not written)
   */
  import java.util.Date
  object DateSerializer extends HTMLSerializer[Date] {
    override def serialize(date: Date): String = s"<div> ${date.toString} </div>"
  }

  /*
    Advantages of Option 3
    2 . Multiple Serializers for a given Type
   */
  // Type class instances
  object PartialUserSerializer extends HTMLSerializer[User] { // <- For NOT logged in User
    override def serialize(user: User): String = s"<div> ${user.name} </div>"
  }

  // This HTML Serializer is Called TYPE CLASS

  /**
    TYPE CLASS Template Demo
    - It specifies a set of operations for e.g. `serialise` that can be applied to a given type.

    - All the implementers of a Type class are called type class instances.
      Now even though the type class instances are types themselves such as `PartialUserSerializer`
      They're called instances because it doesn't make sense to instantiate them multiple times.
      That's why we often use Singleton objects for them.
   */

  /*
      ِExplanation for Type Class Concept

      As a data type a normal class describes a collection of methods and properties that something must have in order to belong to that specific types
      For example if it is of type String then it is known to support `length` operation and the type chequer for the compiler can use this information at compile time to find errors in the source code.
      This is a process called a static type checking

      A type class as opposed to a normal class lifts this same concept to a higher level applying it to types
      So it describes a collection of properties or methods that a type must have in order to belong to that specific type class.

      For example if a type belongs to an `Ordering` Type class, then it is known that instances of that type have the ability to compare values and tell if one is less than another.


   */

  // Example Type Class Template
  trait MyTypeClassTemplate[T] {
    def actions(value: T): String //<- All implementors of this type class template need to supply an implementation of this action
  }

  /*
    Exercise: Equality Type Class
   */

  // Type Class
  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  // Type Class Instance 1
  object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  // Type Class Instance 2
  object FullEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name && a.email == b.email
  }

}
