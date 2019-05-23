package in.kamranali.patternMatching

object AdvancePatternMatching2 extends App {

  /*
    Pattern Matching allows to decompose values that confirms to a pattern
 */

  val numbers = List(1)
  val description = numbers match {
    case head :: Nil => println(s"the only element is $head")// infix pattern
    case _ =>
  }
  // PS: `case head :: Nil` is equivalent to :: case class, `::(head, Nil)`

  // Our own infix pattern
  case class Or[A,B](a: A, b: B) // In scala it is known as `Either`

  val either = Or(2, "two")

  // Normal way
  val humanDescription1 = either match {
    case Or(number, string) => s"$number is written as $string"
  }

  // Magical way (Infix way)
  val humanDescription2 = either match {
    case number Or string => s"$number is written as $string"
  }
  println(humanDescription2)

  // REMEMBER: Infix pattern only works when we have two things in the pattern. For 3, it doesn't make sense

  /*
  Decomposing Sequences
   */

  val vararg = numbers match {
    case List(1, _*) => "Starting with 1" // _* is a vararg pattern
  }
  println(vararg)

  // Pattern Matching against our own types with variable number of arguments
  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }

  case object Empty extends MyList[Nothing]
  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyListPattern {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
      if (list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
  }

  val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val decomposed = myList match {
    case MyListPattern(1,2, _*) => "starting with 1, 2" // As we wrote `1,2, _*` compiler needs an `unapplySeq` which returns Optional Seq
    case _ => "something else"
  }

  println(decomposed)

  /*
  Custom Return Types for unapply/unapplySeq.
  The data structure that we use as a return type of unapply/unapplySeq should have two defined methods
  - isEmpty: Boolean
  - get: something

  `Option` has both of them
   */

  // Let define custom data type
  
  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }

  class Person(val name: String, val age: Int)

  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      override def isEmpty: Boolean = false

      override def get: String = person.name
    }
  }

  val bob = new Person("Bob", 22)
  val extractName = bob match {
    case PersonWrapper(n) => s"This person name is $n"
    case _ => "No name"
  }

  println(extractName)

}
