package in.kamranali.OOP

abstract class MyList[+A] { // Covariant List

  /*
  head = first element of the list
  tail = remainder of the list
  isEmpty = is the list empty
  add(int) = return new list with this element added
  toString => a String representation of the list
   */

  def head: A
  def tail:MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  def printElements: String

  //polymorphic call
  override def toString: String = "[" + printElements + "]"

  def map[B](transformer: MyTransformer[A, B]): MyList[B]

  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]

  def filter(predicate: MyPredicate[A]): MyList[A]

  // Concatenation (will be used in flatMap)
  def ++[B >: A](list: MyList[B]): MyList[B]
}

case object Empty extends MyList[Nothing] { // Because Nothing is a proper substitute of any type.

  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException

  def isEmpty: Boolean = false

  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, this)

  def printElements: String = ""

  def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = Empty

  def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty

  def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty

  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list // Empty Concatenating with something will return that thing

}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {

  def head: A = h
  def tail: MyList[A] = t

  def isEmpty: Boolean = false

  def add[B >: A](element: B): MyList[B] = new Cons(element, this)

  def printElements: String =
    if (t.isEmpty) "" + h
    else h + " " + t.printElements

  def filter(predicate: MyPredicate[A]): MyList[A] =
    if (predicate.test(h)) new Cons(h, t.filter(predicate)) // If head passes the predicate it will be included in the result and we will recurse for tail
    else t.filter(predicate) // else recurse for tail

  def map[B](transformer: MyTransformer[A, B]): MyList[B] =
    new Cons(transformer.transform(h), t.map(transformer))

  def ++[B >: A](list: MyList[B]): MyList[B] = new Cons(h, t ++ list)

  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] =
    transformer.transform(h) ++ t.flatMap(transformer)

}

trait MyPredicate[-T] {
  def test(element: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(element: A): B
}

object ListTest extends App {
  val listOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val anotherListOfIntegers: MyList[Int] = new Cons(4, new Cons(5, Empty))
  val listOfStrings: MyList[String] = new Cons("Hello", new Cons("Scala", new Cons("!", Empty)))

  println(listOfIntegers.toString)
  println(listOfStrings.toString)

  println(listOfIntegers.map(new MyTransformer[Int, Int] {
    override def transform(element: Int): Int = element * 2
  }).toString)

  println(listOfIntegers.filter(new MyPredicate[Int] {
    override def test(element: Int): Boolean = element % 2 == 0
  }).toString)

  println(listOfIntegers ++ anotherListOfIntegers)

  println(listOfIntegers.flatMap(new MyTransformer[Int, MyList[Int]] {
    override def transform(element: Int): MyList[Int] = {
      new Cons(element, new Cons(element + 1, Empty))
    }
  }).toString)
}
