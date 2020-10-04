package in.kamranali.AdvanceTypeSystem

import scala.language.higherKinds

/**
  * Advance Scala Lesson 51 [Higher Kinded Types]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/11053878
  *
  */

object HigherKindedTypes extends App {

  /*
    HigherKindedTypes: Deeper Generic Type with unknown Type parameter at deepest Level
   */

  trait AHigherKindedType[F[_]]

  trait MyList[T] {
    def flatMap[B](f: T => B): MyList[B]
  }

  trait MyOption[T] {
    def flatMap[B](f: T => B): MyOption[B]
  }

  // Let's take the concept of combine/multiply. which is same in all `FlatMappable` Types
  // List(1,2) x List("a","b") => List(1a, 1b, 2a, 2b)

  // for MyList
  def multiply[A, B](listA: List[A], listB: List[B]): List[(A,B)] =
    for {
      a <- listA
      b <- listB
    } yield (a,b)

  // for Option
  def multiply[A, B](listA: Option[A], listB: Option[B]): Option[(A,B)] =
    for {
      a <- listA
      b <- listB
    } yield (a,b)

  // Multiply in `MyList` and `Option` are exactly same

  // So lets come up with common implementation of `multiple` method for completely unrelated types i.e. `MyList` and `Option`

  // Solution is HIGHER KINDED Type

  // Let's have a monad with `map` and `flatMap` (which are required for for-comprehensions) to come up with a single `multiply` method
  trait Monad[F[_], A] { // where `F[_]` is either `MyList` Or `Option` OR ...
    def flatMap[B](f: A => F[B]): F[B]
    def map[B](f: A => B): F[B]
  }

  class MonadList[A](list: List[A]) extends Monad[List, A] { //<- here `F[_]` is `List` Type. Basically its an abstraction over list of A
    override def flatMap[B](f: A => List[B]): List[B] = list.flatMap(f)
    override def map[B](f: A => B): List[B] = list.map(f)
  }

  val monadList = new MonadList(List(1,2,3))
  monadList.flatMap(x => List(x, x+ 1))  //<- it returns List[Int]
  // So from Monad[List, Int] we return by flat mapping List[Int]

  monadList.map(_ * 2) // return List[Int]
  // So from Monad[List, Int] we return by flat mapping List[Int]

  def multiple[F[_], A, B](ma: Monad[F, A], mb: Monad[F, B]): F[(A, B)] = // where `F` is for e.g. `List` Type itself
    for {
      a <- ma
      b <- mb
    } yield (a,b)

  // Later we can say
  val solList = multiple(new MonadList(List(1,2)), new MonadList(List("A","B")))
  println(solList) // List((1,A), (1,B), (2,A), (2,B))

  // Similarly now we can define a Monad for Option as well
  class MonadOption[A](option: Option[A]) extends Monad[Option, A] {
    override def flatMap[B](f: A => Option[B]): Option[B] = option.flatMap(f)
    override def map[B](f: A => B): Option[B] = option.map(f)
  }

  val solOption = multiple(new MonadOption[Int](Some(2)), new MonadOption[String](Some("scala")))
  println(solOption) // Some((2,scala))

  // We can clearly see we have ONLY ONE Definition of `multiple` for all Monad Kinds e.g. `MonadList`, `MonadOption` etc. All we need to do is to use a Wrapper (e.g. Monad) instead of those types e.g. List, Option

  // Is there a way to auto convert our Types to Monad Wrapper Counter Parts ??

  // YES, We can

  // By using implicit parameters

  implicit class MonadList2[A](list: List[A]) extends Monad[List, A] {
    override def flatMap[B](f: A => List[B]): List[B] = list.flatMap(f)
    override def map[B](f: A => B): List[B] = list.map(f)
  }

  implicit class MonadOption2[A](option: Option[A]) extends Monad[Option, A] {
    override def flatMap[B](f: A => Option[B]): Option[B] = option.flatMap(f)
    override def map[B](f: A => B): Option[B] = option.map(f)
  }

  def multiple2[F[_], A, B](ma: Monad[F, A], mb: Monad[F, B]): F[(A, B)] =
    for {
      a <- ma
      b <- mb
    } yield (a,b)

  /*
      trait Monad[F[_], A] {
        def flatMap[B](f: A => F[B]): F[B]
        def map[B](f: A => B): F[B]
      }

      Our Monad trait now behaves as Higher Kinded Type Class


   */
  // So now we can say

  val solList2 = multiple2(List(1,2), List("A","B"))
  println(solList2) // List((1,A), (1,B), (2,A), (2,B))

  val solOption2 = multiple2(Some(2), Some("scala"))
  println(solOption2) // Some((2,scala))

}
