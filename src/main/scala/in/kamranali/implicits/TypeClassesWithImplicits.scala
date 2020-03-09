package in.kamranali.implicits

object TypeClassesWithImplicits extends App {

  trait HTMLSerializer[T] { // <- TYPE Class with Type T
    def serialize(value: T): String
  }

  object HTMLSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String = {
      serializer.serialize(value)
    }
  }

  // Method 1 Explicitly passing `IntSerializer1`
  object IntSerializer1 extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div> $value </div>"
  }

  println(HTMLSerializer.serialize[Int](42)(IntSerializer1))

  // Method 2 (Using Implicit so that compiler can figure it our for us)
  implicit object IntSerializer2 extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div> $value </div>"
  }

  println(HTMLSerializer.serialize[Int](42)) // compiler will figure our `IntSerializer2`

  /*
    Equality Type Class with implicit
   */

  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  object Equal {
    def apply[T](implicit  equalizer: Equal[T]) = equalizer

    def checkEquality[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean = equalizer.apply(a, b)

    // Above `apply` pattern surfaces the entire Equal Type Class. So that we can have access to all the methods in it.
  }

  implicit object IntEquality extends Equal[Int] {
    override def apply(a: Int, b: Int): Boolean = a == b
  }
  println(Equal.checkEquality(3, 4))

  // way 1
  println(Equal.apply[Int].apply(3, 4))

  // way 2
  println(Equal[Int].apply(3, 4))

  // way 3
  println(Equal[Int](IntEquality).apply(3, 4))

  /*
    AD - HOC polymorphism
   */

  trait AnotherEqual[T] {
    def apply(a: T, b: T): Boolean
  }

  object AnotherEqual {
    def apply[T](a: T, b: T)(implicit  equalizer: Equal[T]) = equalizer.apply(a,b)
  }

  implicit object IntAnotherEquality extends AnotherEqual[Int] {
    override def apply(a: Int, b: Int): Boolean = a == b
  }

  // `AnotherEqual(3, 3)` -> AD - HOC polymorphism
  // Here we have called `AnotherEqual` on Int but if we have `AnotherEqual[User]` we can call `AnotherEqual` on User as well
  // This is polymorphism because depending on the actual TYPE of value being compared the compiler fetches the correct TYPE Class Instance for our types
  println(AnotherEqual(3, 3))
}
