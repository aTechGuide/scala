package in.kamranali.implicits

/**
  * Advance Scala Lesson 36 [Type Classes, Part 2]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/11053824#content
  */

object TypeClassesWithImplicits extends App {

  trait HTMLSerializer[T] { // <- TYPE Class with Type T
    def serialize(value: T): String
  }

  object HTMLSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String = {
      serializer.serialize(value)
    }

    // Factory method
    // It surface out the implicit value HTMLSerializer of Type T
    def apply[T](implicit serializer: HTMLSerializer[T]) = serializer
  }

  // Method 1 Explicitly passing `IntSerializer1`
  object IntSerializer1 extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div> $value </div>"
  }

  println(HTMLSerializer.serialize[Int](42)(IntSerializer1)) // <div> 42 </div>

  // Method 2 (Using Implicit so that compiler can figure it our for us)
  implicit object IntSerializer2 extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div> $value </div>"
  }

  println(HTMLSerializer.serialize[Int](42)) // compiler will figure our `IntSerializer2` and prints "<div> 42 </div>"
  // The advantage of this designing is that we can simply say `HTMLSerializer.serialize` any value for which we have defined an HTMLSerializer as an implicit object

  // Better design will be to have an `applu` method inside HTMLSerializer which exposes the appropriate Serializer
  println(HTMLSerializer[Int].serialize(42))
  // ^ We have access to the entire type class interface so not only to the `serialise` method but maybe to other methods as well.

  // Full Template of Type Class
  trait MyTypeClassTemplate[T] {
    def actions(value: T): String //<- All implementors of this type class template need to supply an implementation of this action
  }

  object MyTypeClassTemplate {
    def apply[T](implicit instance: MyTypeClassTemplate[T]): MyTypeClassTemplate[T] = instance
  }


  /*
    Exercise
    Equality Type Class with implicit
   */

  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean = equalizer.apply(a, b)
  }

  implicit object IntEquality extends Equal[Int] {
    override def apply(a: Int, b: Int): Boolean = a == b
  }

  println(Equal(3, 4)) // false


  /*
    AD - HOC polymorphism

    Simple class polymorphism doesn't allow us to achieve this functionality as was the case with HTMLSerializer we wrote at the beginning of the type class
    We achieve polymorphism in the sense that if two distinct or potentially unrelated types have equalizer's implemented then we can call this `Equal(T, T)` thing on them regardless of their type.

    So we have `Equal` on `Int` for example and if we have an implicit equalizer for some other type e.g. `User` we can still call equal on those types as well.
    This is Polymorphism because depending on the actual type of the values being compared the compiler takes care to fetch the correct type class instance for our types.
   */

  // Defining Equal for User Class
  case class User(name: String, age: Int, email: String)
  val john = User("John", 13, "johan@abc.con")
  val sam = User("Sam", 1, "sam@abc.con")

  implicit object UserAgeEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.age == b.age
  }

  println(Equal(john, sam)) // false
}
