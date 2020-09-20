package in.kamranali.collections.custom

import scala.annotation.tailrec

/**
  * Advance Scala Lesson 10, 11, 12 [Functional Set]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/10937352
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/10937358
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/10937362
  */

trait MySet[A] extends (A => Boolean) {

  def contains(elem: A): Boolean
  def +(elem: A): MySet[A]
  def ++(anotherSet: MySet[A]): MySet[A]  // Union

  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B]
  def filter(predicate: A => Boolean): MySet[A]

  def foreach(f: A => Unit): Unit

  // As MySet[A] extends A => Boolean; It has an apply method which we will implement in trait itself
  override def apply(elem: A): Boolean = contains(elem)

  /*
    Enhancements
   */
  def -(elem: A): MySet[A] // removing an element
  def --(anotherSet: MySet[A]): MySet[A] // difference
  def &(anotherSet: MySet[A]): MySet[A] // intersection

  /*
    Negating a finite number of elements leads to infinite number of elements
   */
  def unary_! : MySet[A] // Negation of set
}

// We have chose EmptySet[A] to be a class NOT a singleton  `object` extending MySet[Nothing] because
// MySet is NOT Co Variant its invariant
class EmptySet[A] extends MySet[A] {
  override def contains(elem: A): Boolean = false

  override def +(elem: A): MySet[A] = new NonEmptySet[A](elem, this)

  override def ++(anotherSet: MySet[A]): MySet[A] = anotherSet

  override def map[B](f: A => B): MySet[B] = new EmptySet[B]

  override def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]

  override def filter(predicate: A => Boolean): MySet[A] = this

  override def foreach(f: A => Unit): Unit = () // `()` is Unit value

  override def -(elem: A): MySet[A] = this

  override def --(anotherSet: MySet[A]): MySet[A] = this

  override def &(anotherSet: MySet[A]): MySet[A] = this

  def unary_! : MySet[A] = new PropertyBasedSet[A](_ => true) // Negating an empty set will return a set including every single element of Type A
}

// AllInclusiveSet is now a special case of PropertyBasedSet
/*
class AllInclusiveSet[A] extends MySet[A] {
  override def contains(elem: A): Boolean = true

  override def +(elem: A): MySet[A] = this

  override def ++(anotherSet: MySet[A]): MySet[A] = this

  override def map[B](f: A => B): MySet[B] = ???

  override def flatMap[B](f: A => MySet[B]): MySet[B] = ???

  override def filter(predicate: A => Boolean): MySet[A] = ???

  override def foreach(f: A => Unit): Unit = ???

  override def -(elem: A): MySet[A] = ???

  override def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)

  override def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)

  override def unary_! : MySet[A] = new EmptySet[A]
}
*/

// This class denotes every element of Type A which satisfies a property i.e. { x in A | property(x) }
// `AllInclusiveSet` is an special case of `PropertyBasedSet` where every element in domain A, the function of property returns true
class PropertyBasedSet[A](property: A => Boolean) extends MySet[A] {
  override def contains(elem: A): Boolean = property(elem)

  // { x in A | property(a) } + element = { x in A | property(a) || x == element}
  override def +(elem: A): MySet[A] =
    new PropertyBasedSet[A](x => property(x) || x == elem)

  // { x in A | property(a) } ++ set = { x in A | property(a) || set contains x}
  override def ++(anotherSet: MySet[A]): MySet[A] =
    new PropertyBasedSet[A](x => property(x) || anotherSet(x))

  // If we map an infinite set with a function, we don;t know the resulting set is finite or not
  // e.g. all integers map (_ % 3) => [0 1 2]
  override def map[B](f: A => B): MySet[B] = politelyFail

  override def flatMap[B](f: A => MySet[B]): MySet[B] = politelyFail

  override def filter(predicate: A => Boolean): MySet[A] =
    new PropertyBasedSet[A](x => property(x) && predicate(x))

  override def foreach(f: A => Unit): Unit = politelyFail

  override def -(elem: A): MySet[A] = filter(x => x != elem)

  override def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)

  override def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)

  override def unary_! : MySet[A] = new PropertyBasedSet[A](x => !property(x))

  def politelyFail = throw new IllegalArgumentException("Really deep rabbit hole!")
}

class NonEmptySet[A](head: A, tail: MySet[A]) extends MySet[A] {
  override def contains(elem: A): Boolean =
    elem == head || tail.contains(elem)

  override def +(elem: A): MySet[A] =
    if (this contains elem) this
    else new NonEmptySet[A](elem, this)

  /*
  [1 2 3] ++ [4 5] =
    [2 3] ++ [4 5] + 1 =
      [3] ++ [4 5] + 1 + 2 =
        [] ++ [4 5] + 1 + 2 + 3 =
          [4 5] + 1 + 2 + 3 = [4 5 1 2 3]
   */
  override def ++(anotherSet: MySet[A]): MySet[A] =
    tail ++ anotherSet + head

  override def map[B](f: A => B): MySet[B] = (tail map f) + f(head)

  override def flatMap[B](f: A => MySet[B]): MySet[B] = (tail flatMap f) ++ f(head)

  override def filter(predicate: A => Boolean): MySet[A] = {
    val filteredTail = tail filter predicate

    if (predicate(head)) filteredTail + head
    else filteredTail
  }

  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail foreach f
  }

  override def -(elem: A): MySet[A] =
    if (head == elem) tail
    else tail - elem + head

  override def --(anotherSet: MySet[A]): MySet[A] = filter(x => !anotherSet(x)) // Remember `!anotherSet.contains(x)` === `!anotherSet(x)`

  override def &(anotherSet: MySet[A]): MySet[A] = filter(x => anotherSet(x)) // Remember `anotherSet.contains(x)` === `anotherSet(x)`
  // PS: intersection and filtering are same as our set is functional

  def unary_! : MySet[A] = new PropertyBasedSet[A](x => !this.contains(x))
}

// Just for the convenience of building Set we are creating a companion object
object MySet {
  def apply[A](values: A*): MySet[A] = {

    @tailrec
    def buildSet(valSeq: Seq[A], acc: MySet[A]): MySet[A] =
      if (valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc + valSeq.head)

    buildSet(values.toSeq, new EmptySet[A])
  }
}

object SetPlayground extends App {
  val s = MySet(1,2,3,4)

  s + 5 ++ MySet(-1, -2) + 3 map (x => x * 10) filter (_ % 2 == 0) foreach println

  val negative = !s //s.unary_! = all the naturals not equal to 1 2 3 4
  println(negative(2)) // false
  println(negative(5)) // true

  val negativeEven = negative.filter(_ % 2 == 0)
  println(negativeEven(5)) // false

  val negativeEven5 = negativeEven + 5 // all even numbers bigger > 4 + 5
  println(negativeEven5(5)) // true

  val subs = s -- MySet(3, 4, 5, 6)
  subs foreach println

}
