package in.kamranali.implicits

object TypeClassDeeper extends App {

  trait HTMLSerializer[T] { // <- TYPE Class with Type T
    def serialize(value: T): String
  }

  object UserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String = s"<div> ${user.name} (${user.age} yo) <a href=${user.email}/> </div>"
  }

  case class User(name: String, age: Int, email: String) //<- NO `toHTML` method

  /*
    Conversions with Type classes
   */
  implicit class HTMLEnrichment[T](value: T) {
    def toHTML(serializer: HTMLSerializer[T]): String = serializer.serialize(value)
  }

  val john = User("John", 13, "johan@abc.con")
  println(john.toHTML(UserSerializer)) // <- Compiler: println(new HTMLEnrichment[User](john).toHTML(UserSerializer))

  // Please serialize `john` with `UserSerializer`. So Compiler tries to wrap `john` into whatever implicit has the `toHTML` method taking `UserSerializer`
  // here `toHTML` method is implicit as `john` was wrapped into `HTMLEnrichment[User]`

  // One Step further If HTMLEnrichment class has toHTML using implicit keyword
    // def toHTML(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(value)
    // then an `implicit` UserSerializer can be taken in as
      // println(john.toHTML)

  /*
    Above Feature of `implicit class` solves following problems
    1. We can extend functionality of new types (example below)
    2. We can have different implementations for same type
      2.a if we are using implicits in `toHTML` then we can import implicit serializer into local scope
      2.b Without implicits, Passing it explicitely
    3. Super Expressive
   */

  // 1. Example: We can extend functionality for Int class

  object IntSerializer extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div> $value </div>"
  }

  println(1.toHTML(IntSerializer))

  // 2. Example:
  object PartialUserSerializer extends HTMLSerializer[User] { // <- For NOT logged in User
    override def serialize(user: User): String = s"<div> ${user.name} </div>"
  }

  println(john.toHTML(PartialUserSerializer))

  /*
    Exercise

    Enhance Equality class with === & !== Method
   */

  // Type Class
  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  // Type Class Instance
  implicit object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  // CONVERSION with implicit classes
  implicit class TypeSafeEqual[T](value: T) {
    def ===(anotherValue: T)(implicit equal: Equal[T]) = equal.apply(value, anotherValue)

    def !==(anotherValue: T)(implicit equal: Equal[T]) = !equal.apply(value, anotherValue)
  }

  val john1 = User("John", 13, "johan@abc.con")
  val john2 = User("John", 13, "johan@abc.con")

  println(john1 === john2)

  // Compiler Conversion

  // john1 === john2
    // john1.===(john2)
      // new TypeSafeEqual[User](john1).===(john2)
        // new TypeSafeEqual[User](john1).===(john2)(NameEquality)


  /*
    Benefits
    1 - Type Safety
   */
  println(john == 43) // <- Allowed

  // println(john === 43) // <- Fails, Compiler forces that `john` and `43` should be of same Type

}
