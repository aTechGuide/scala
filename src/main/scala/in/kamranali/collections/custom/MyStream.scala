package in.kamranali.collections.custom

import scala.annotation.tailrec

abstract class MyStream[+A] {

  def isEmpty: Boolean
  def head: A
  def tail: MyStream[A]

  def #::[B >: A](element: B): MyStream[B] // prepend operator
  def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] // concatenate two streams

  def foreach(f: A => Unit): Unit
  def map[B](f: A => B): MyStream[B]
  def flatMap[B](f: A => MyStream[B]): MyStream[B]
  def filter(predicate: A => Boolean): MyStream[A]

  def take(n: Int): MyStream[A] // takes the first n elements out of this stream and returns it
  def takeAsList(n: Int): List[A] = take(n).toList()

  /*
  [1 2 3 ].toList([]) =
    [2 3].toList([1]) =
      [3].toList([2 1]) =
        [].toList([3 2 1]) =
          [1 2 3]
   */
  @tailrec
  final def toList[B >: A](acc: List[B] = Nil): List[B] =
    if (isEmpty) acc.reverse
    else tail.toList(head  :: acc)
}

object EmptyStream extends MyStream[Nothing] {
  override def isEmpty: Boolean = true

  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyStream[Nothing] = throw new NoSuchElementException

  override def #::[B >: Nothing](element: B): MyStream[B] = new ConsStream[B](element, this)

  override def ++[B >: Nothing](anotherStream: => MyStream[B]): MyStream[B] = anotherStream

  override def foreach(f: Nothing => Unit): Unit = ()

  override def map[B](f: Nothing => B): MyStream[B] = this

  override def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = this

  override def filter(predicate: Nothing => Boolean): MyStream[Nothing] = this

  override def take(n: Int): MyStream[Nothing] = this
}

class ConsStream[+A](hd: A, tl: => MyStream[A]) extends MyStream[A] { // without call by Name (: =>) stream will not be lazily evaluated
  override def isEmpty: Boolean = false

  override val head: A = hd

  override lazy val tail: MyStream[A] = tl  // `: =>` + `lazy` == CALL BY NEED

  override def #::[B >: A](element: B): MyStream[B] = new ConsStream(element, this)

  override def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] = new ConsStream(head, tail ++ anotherStream)

  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }

  override def map[B](f: A => B): MyStream[B] = new ConsStream(f(head), tail.map(f))  // preserves lazy evaluation

  override def flatMap[B](f: A => MyStream[B]): MyStream[B] = f(head) ++ tail.flatMap(f)

  override def filter(predicate: A => Boolean): MyStream[A] = { // preserves lazy evaluation
    if (predicate(head)) new ConsStream(head, tail.filter(predicate))
    else tail.filter(predicate)
  }

  override def take(n: Int): MyStream[A] =
    if (n <= 0) EmptyStream
    else if (n == 1) new ConsStream(head, EmptyStream)
    else new ConsStream(head, tail.take(n - 1)) // preserves lazy evaluation
}

object MyStream {
  def from[A](start: A)(generator: A => A): MyStream[A] =
    new ConsStream(start, MyStream.from(generator(start))(generator))
}

object StreamPlayground extends App {
  val naturals = MyStream.from(1)(_ + 1)

  println(naturals.head)
  println(naturals.tail.head)
  println(naturals.tail.tail.head)

  val startFrom0 = 0 #:: naturals
  println(startFrom0.head)

  startFrom0.take(10000).foreach(println)

  println(startFrom0.map(_ * 2).take(100).toList())

  println(startFrom0.flatMap(x => new ConsStream(x, new ConsStream(x + 1, EmptyStream))).take(10).toList())

  println(startFrom0.filter(_ < 10).take(10).toList())

  // Streams of Fibonacci numbers
  def fibonacci(first: BigInt, second: BigInt): MyStream[BigInt] =
    new ConsStream[BigInt](first, fibonacci(second, first + second))

  println(fibonacci(1,1).take(10).toList())

  // Stream of prime numbers with Eratosthenes' sieve
  def eratosthenes(numbers: MyStream[Int]): MyStream[Int] =
    if (numbers.isEmpty) numbers
    else new ConsStream[Int](numbers.head, eratosthenes(numbers.tail.filter(_ % numbers.head != 0)))

  println(eratosthenes(MyStream.from(2)(_ + 1)).take(20).toList())

}