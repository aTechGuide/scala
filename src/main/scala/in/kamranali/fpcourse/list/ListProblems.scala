package in.kamranali.fpcourse.list

import scala.annotation.tailrec

sealed abstract class RList[+T] {
  def head: T
  def tail: RList[T]
  def isEmpty: Boolean

  // Scala methods ending with :: are right associative
  def ::[S >: T](element: S): RList[S] = new ::(element, this) // Common impl for both RNil and Cons

  def apply(index: Int): T
}

case object RNil extends RList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException
  override def tail: RList[Nothing] = throw new NoSuchElementException
  override def isEmpty: Boolean = true

  override def toString: String = "[]"

  // GET K-th element
  override def apply(index: Int): Nothing = throw new NoSuchElementException
}

case class ::[+T](override val head: T, override val tail: RList[T]) extends RList[T] {

  override def isEmpty: Boolean = false
  override def toString: String = {

    @tailrec // As whenever recursion occurs its the last expression in its code branch
    def toStringTailRec(remaining: RList[T], result: String): String = {
      if (remaining.isEmpty) result
      else if (remaining.tail.isEmpty) s"$result${remaining.head}"
      else toStringTailRec(remaining.tail, s"$result${remaining.head}, ")
    }

    "[" + toStringTailRec(this, "") + "]"

  }

  // Complexity -> O(N)
  override def apply(index: Int): T = {
    @tailrec
    def applyTailRec(idx: Int, remaining: RList[T]): T = {
      if (idx == index) remaining.head
      else applyTailRec(idx + 1, remaining.tail)
    }

    if (index < 0) throw new NoSuchElementException
    else applyTailRec(0, this)
  }

}

object ListProblems extends App {

  val aSmallList = 1 :: 2 :: 3 :: RNil // ::(1, ::(2, ::(3, RNil)))
  println(aSmallList)
  println(aSmallList(2)) // 3

}
