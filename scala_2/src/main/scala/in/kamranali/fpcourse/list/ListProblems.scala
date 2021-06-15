package in.kamranali.fpcourse.list

import scala.annotation.tailrec
import scala.util.Random
// Where ever recursive call occurs its the last expression of its code branch

sealed abstract class RList[+T] {
  def head: T
  def tail: RList[T]
  def isEmpty: Boolean

  // Scala methods ending with :: are right associative
  def ::[S >: T](element: S): RList[S] = new ::(element, this) // Common impl for both RNil and Cons

  // GET K-th element
  def apply(index: Int): T

  // Calculate Length of List
  def length: Int

  // Reverse the List
  def reverse: RList[T]

  // concatenate another list to this one
  def ++[S >: T](anotherList: RList[S]): RList[S]

  // remove the Kth Element
  def removeAt(index: Int): RList[T]

  // Implement the Big 3
  def map[S](f: T => S): RList[S]
  def flatMap[S](f: T => RList[S]): RList[S]
  def filter(f: T => Boolean): RList[T]

  /**
    * Medium
    */
  // Run length Encoding: Count consecutive duplicates and return them in a list of tuples
  def rle: RList[(T, Int)]

  // Given a list duplicate each element a number of times in a row
  def duplicateEach(k: Int): RList[T]

  // rotation by a number of positions to the left
  def rotate(k: Int): RList[T]

  // return k random sample in a new List, K can be larger than the list length
  def sample(k: Int): RList[T]

  /**
    * Hard problems
    */
  // sorting the list in the order defined by the Ordering object
  def insertionSort[S >: T](ordering: Ordering[S]): RList[S]
  def mergeSort[S >: T](ordering: Ordering[S]): RList[S]

}

case object RNil extends RList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException
  override def tail: RList[Nothing] = throw new NoSuchElementException
  override def isEmpty: Boolean = true

  override def toString: String = "[]"
  override def apply(index: Int): Nothing = throw new NoSuchElementException
  override def length: Int = 0
  override def reverse: RList[Nothing] = this
  override def ++[S >: Nothing](anotherList: RList[S]): RList[S] = anotherList
  override def removeAt(index: Int): RList[Nothing] = this
  override def map[S](f: Nothing => S): RList[S] = this
  override def flatMap[S](f: Nothing => RList[S]): RList[S] = this
  override def filter(f: Nothing => Boolean): RList[Nothing] = this
  override def rle: RList[(Nothing, Int)] = this
  override def duplicateEach(k: Int): RList[Nothing] = this
  override def rotate(k: Int): RList[Nothing] = this
  override def sample(k: Int): RList[Nothing] = this
  override def insertionSort[S >: Nothing](ordering: Ordering[S]): RList[S] = this
  override def mergeSort[S >: Nothing](ordering: Ordering[S]): RList[S] = this
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

  // Complexity -> O(N)
  override def length: Int = {
    @tailrec
    def lengthTailRec(remaining: RList[T], accumulator: Int): Int = {
      if (remaining.isEmpty) accumulator
      else lengthTailRec(remaining.tail, accumulator + 1)
    }

    lengthTailRec(this, 0)
  }

  // Complexity -> O(N)
  override def reverse: RList[T] = {
    @tailrec
    def reverseTailrec(remaining: RList[T], accumulator: RList[T]): RList[T] = {
      if (remaining.isEmpty) accumulator
      else reverseTailrec(remaining.tail, remaining.head :: accumulator)
    }

    reverseTailrec(this, RNil)
  }

  // Complexity -> O(anotherList) + O(N + anotherList)
  override def ++[S >: T](anotherList: RList[S]): RList[S] = {
    @tailrec
    def concatTailRec(remainingList: RList[S], accum: RList[S]): RList[S] = {
      if (remainingList.isEmpty) accum
      else concatTailRec(remainingList.tail, remainingList.head :: accum)
    }

    concatTailRec(anotherList, this.reverse).reverse
  }

  // Complexity -> O(N)
  def removeAt(index: Int): RList[T] = {
    @tailrec
    def removeTailRec(idx: Int, remaining: RList[T], predecessors: RList[T]): RList[T] = {

      if (remaining.isEmpty) predecessors.reverse
      else if (idx == index) predecessors.reverse ++ remaining.tail
      else removeTailRec(idx + 1, remaining.tail, remaining.head :: predecessors) // Since we have only prepend Operator
    }

    if (index < 0) this
    else removeTailRec(0, this, RNil)
  }

  // Complexity -> O(N)
  override def map[S](f: T => S): RList[S] = {
    @tailrec
    def mapTailrec(remaining: RList[T], accumulator: RList[S]): RList[S] = {

      if (remaining.isEmpty) accumulator.reverse
      else mapTailrec(remaining.tail, f(remaining.head) :: accumulator)
    }

    mapTailrec(this, RNil)
  }

  // Complexity -> O(N * M)
  override def flatMap[S](f: T => RList[S]): RList[S] = {
    @tailrec
    def flatMapTailrec(remaining: RList[T], accumulator: RList[S]): RList[S] = {

      if (remaining.isEmpty) accumulator.reverse
      else flatMapTailrec(remaining.tail, f(remaining.head).reverse ++ accumulator)
    }

    /*
      [1,2,3].flatMap(x => [x, 2 * x]) = betterFlatMap([1,2,3], [])
      = betterFlatMap([2,3], [[2,1]])
      = betterFlatMap([3], [[4,2], [2,1]])
      = betterFlatMap([], [[6,3], [4,2], [2,1]])
      = concatenateAll([[6,3], [4,2], [2,1]], [], [])
      = concatenateAll([[4,2], [2,1]], [6,3], [])
      = concatenateAll([[4,2], [2,1]], [3], [6])
      = concatenateAll([[4,2], [2,1]], [], [3,6])
      = concatenateAll([[2,1]], [4,2], [3,6])
      = concatenateAll([[2,1]], [2], [4,3,6])
      = concatenateAll([[2,1]], [], [2,4,3,6])
      = concatenateAll([], [2,1], [2,4,3,6])
      = concatenateAll([], [1], [2,2,4,3,6])
      = concatenateAll([], [], [1,2,2,4,3,6])
      = [1,2,2,4,3,6]
      Complexity: O(N + Z)

     */
    @tailrec
    // Complexity -> O(N + Z) ~> O(Z)
    def betterFlatmap(remaining: RList[T], accum: RList[RList[S]]): RList[S] = {
      if (remaining.isEmpty) concatenateAll(accum, RNil, RNil)
      else betterFlatmap(remaining.tail, f(remaining.head).reverse :: accum)
    }

    @tailrec
    // Complexity -> O(Z)
//    Where
//    z = dimension of the result
//    = Concatenation of the application of f() on every single element of the original list
    def concatenateAll(elements: RList[RList[S]], currList: RList[S], accum: RList[S]): RList[S] = {
      if (currList.isEmpty && elements.isEmpty) accum
      else if (currList.isEmpty) concatenateAll(elements.tail, elements.head, accum)
      else concatenateAll(elements, currList.tail, currList.head :: accum)
    }

    // flatMapTailrec(this, RNil)
    betterFlatmap(this, RNil)
  }

  // Complexity -> O(N)
  override def filter(predicate: T => Boolean): RList[T] = {
    @tailrec
    def filterTailrec(remaining: RList[T], predecessors: RList[T]): RList[T] = {

      if (remaining.isEmpty) predecessors.reverse
      else if (predicate(remaining.head)) filterTailrec(remaining.tail, remaining.head :: predecessors)
      else filterTailrec(remaining.tail, predecessors)
    }

    filterTailrec(this, RNil)
  }

  // Complexity -> O(N)
  override def rle: RList[(T, Int)] = {
    @tailrec
    def rleTailrec(remaining: RList[T], res: RList[(T, Int)]): RList[(T, Int)] = {
      if (remaining.isEmpty) res.reverse
      else if (res.isEmpty) rleTailrec(remaining.tail, (remaining.head, 1) :: res)
      else {
        val newRes = if (res.head._1 != remaining.head) (remaining.head, 1) :: res
        else (remaining.head, res.head._2 + 1) :: res.tail

        rleTailrec(remaining.tail, newRes)
      }
    }

    rleTailrec(this, RNil)
  }

  // Complexity -> O(N * K)
  override def duplicateEach(k: Int): RList[T] = {
    @tailrec
    def deTailrec(remaining: RList[T], currElement: T, nDuplications: Int, accumulator: RList[T]): RList[T] = {
      if (remaining.isEmpty && nDuplications == k) accumulator.reverse
      else if (remaining.isEmpty) deTailrec(remaining, currElement, nDuplications + 1, currElement :: accumulator)
      else if (nDuplications == k) deTailrec(remaining.tail, remaining.head, 0, accumulator)
      else deTailrec(remaining, currElement, nDuplications + 1, currElement :: accumulator)

    }
    deTailrec(this.tail, this.head, 0, RNil)
  }

  // Complexity -> O(Max(N * K))
  override def rotate(k: Int): RList[T] = {
    @tailrec
    def rotateTailrec(remaining: RList[T], rotationLeft: Int, buffer: RList[T]): RList[T] = {
      if (remaining.isEmpty && rotationLeft == 0) this
      else if (remaining.isEmpty) rotateTailrec(this, rotationLeft, RNil)
      else if (rotationLeft == 0) remaining ++ buffer.reverse
      else rotateTailrec(remaining.tail, rotationLeft - 1, remaining.head :: buffer)

    }
    rotateTailrec(this, k, RNil)
  }

  // Complexity -> O(N * K)
  override def sample(k: Int): RList[T] = {
    val random = new Random(System.currentTimeMillis())
    val maxIdx = this.length

    @tailrec
    // Complexity -> O(N * K)
    def sampleTailrec(nRemaining: Int, acc: RList[T]): RList[T] = {
      if (nRemaining == 0) acc
      else {
        val index = random.nextInt(maxIdx)
        val newNumber = this(index)
        sampleTailrec(nRemaining - 1, newNumber :: acc)
      }
    }

    // Complexity -> O(N * K)
    def sampleElegant: RList[T] = {
      RList.from(
        (1 to k)
          .map(_ => random.nextInt(maxIdx))
          .map(idx => this (idx))
      )
    }

      if (k < 0) RNil
      else sampleElegant
    }

  override def insertionSort[S >: T](ordering: Ordering[S]): RList[S] = {

    /*
      insertSorted(4, [], [1,2,3,5]) =
      insertSorted(4, [1], [2,3,5]) =
      insertSorted(4, [2,1], [3,5]) =
      insertSorted(4, [3,2,1], [5]) =
      [3,2,1].reverse + (4 :: [5]) =
      [1,2,3,4,5]
      Complexity: O(N)
     */
    @tailrec
    def insertSorted(element: T, before: RList[S], after: RList[S]): RList[S] = {
      if (after.isEmpty || ordering.lteq(element, after.head)) before.reverse ++ (element :: after)
      else insertSorted(element, after.head :: before, after.tail)
    }
    /*
      [3,1,4,2,5].sorted = insertSortTailrec([3,1,4,2,5], []) =
        = insertSortTailrec([1,4,2,5], [3])
        = insertSortTailrec([4,2,5], [1,3])
        = insertSortTailrec([2,5], [1,3,4])
        = insertSortTailrec([5], [1,2,3,4])
        = insertSortTailrec([], [1,2,3,4,5])
        = [1,2,3,4,5]
        Complexity: O(N^2)
     */
    @tailrec
    def insertSortTailrec(remaining: RList[T], acc: RList[S]): RList[S] = {
      if (remaining.isEmpty) acc
      else insertSortTailrec(remaining.tail, insertSorted(remaining.head, RNil, acc))
    }

    insertSortTailrec(this, RNil)
  }

  override def mergeSort[S >: T](ordering: Ordering[S]): RList[S] = {

    /*
      merge([1,3], [2,4,5,6,7], []) =
      merge([3], [2,4,5,6,7], [1]) =
      merge([3], [4,5,6,7], [2,1]) =
      merge([], [4,5,6,7], [3,2,1]) =
      [1,2,3] ++ [4,5,6,7] =
      [1,2,3,4,5,6,7]
     */
    @tailrec
    def merge(listA: RList[S], listB: RList[S], acc: RList[S]): RList[S] = {
      if (listA.isEmpty) acc.reverse ++ listB
      else if (listB.isEmpty) acc.reverse ++ listA
      else if (ordering.lteq(listA.head, listB.head)) merge(listA.tail, listB, listA.head :: acc)
      else merge(listA, listB.tail, listB.head :: acc)
    }

    /*
      [3,1,2,5,4] => [[3],[1],[2],[5],[4]]
      mst([[3],[1],[2],[5],[4]], []) =
      = mst([[2],[5],[4]], [[1,3]])
      = mst([[4]], [[2,5], [1,3]])
      = mst([], [[4], [2,5], [1,3]]) =
      = mst([[4], [2,5], [1,3]], [])
      = mst([[1,3]], [[2,4,5]])
      = mst([], [[1,3], [2,4,5]])
      = mst([[1,3], [2,4,5]], [])
      = mst([], [[1,2,3,4,5]])
      = [1,2,3,4,5]
      Complexity: O(n * log(n))
      complexity(n) = 2 * complexity(n/2) + n
     */
    @tailrec
    def mergeSortTailrec(smallLists: RList[RList[S]], bigLists: RList[RList[S]]): RList[S] = {
      if (smallLists.isEmpty) {
        if (bigLists.isEmpty) RNil
        else if (bigLists.tail.isEmpty) bigLists.head
        else mergeSortTailrec(bigLists, RNil)
      }
      else if (smallLists.tail.isEmpty) {
        if (bigLists.isEmpty) smallLists.head
        else mergeSortTailrec(smallLists.head :: bigLists, RNil)
      }
      else {
        val first = smallLists.head
        val second = smallLists.tail.head
        val merged = merge(first, second, RNil)

        mergeSortTailrec(smallLists.tail.tail, merged :: bigLists)
      }
    }

    // add call

    mergeSortTailrec(this.map(x => x :: RNil), RNil)
  }

}

object RList {
  def from[T](iterable: Iterable[T]): RList[T] = {
    @tailrec
    def convertToRListTailrec(remaining: Iterable[T], acc: RList[T]): RList[T] = {
      if (remaining.isEmpty) acc
      else convertToRListTailrec(remaining.tail, remaining.head :: acc)
    }

    convertToRListTailrec(iterable, RNil).reverse
  }
}
object ListProblems extends App {

  val aSmallList = 1 :: 2 :: 3 :: 4 :: RNil // ::(1, ::(2, ::(3, RNil)))
  val oneToTen = RList.from(1 to 10)
  val aLargeList = RList.from(1 to 10000)
  val anUnorderedList = 3 :: 1 ::2 :: 4 :: 5 :: RNil

  def testEasyProblems(): Unit = {
    println(aSmallList)
    println(aSmallList(2)) // 3
    println(aSmallList.length) // 3
    println(aSmallList.reverse) // [3, 2, 1]
    println(RList.from(1 to 5)) // [1, 2, 3, 4, 5]
    println(aSmallList ++ (5 :: 6 :: RNil)) // [1, 3, 4]
    println(aSmallList.removeAt(1))
    println(aSmallList.map(_ * 2)) // [2, 4, 6, 8]
    println(aSmallList.flatMap(x => x :: (2 * x) :: RNil)) // [1, 2, 2, 4, 3, 6, 4, 8]
    println(aSmallList.filter(_ %2 == 1)) // [1, 3]
  }

  def testMediumProblems(): Unit = {
    // rle
    println((1 :: 1 :: 1 :: 2 :: 2 :: 2 :: 5 :: RNil).rle) // [(1,3), (2,3), (5,1)]

    // Duplicate Each
    println(aSmallList.duplicateEach(2)) // [1, 1, 2, 2, 3, 3, 4, 4]

    // rotate list
    println(aSmallList.rotate(2)) // [3, 4, 1, 2]

    // sample
    println(aSmallList.sample(2)) // [3, 2]
  }

  def testHardProblems(): Unit = {

    println(anUnorderedList.insertionSort(ordering))
  }

  val ordering = Ordering.fromLessThan[Int](_ < _)
  val listToSort = aLargeList.sample(10)

  println(listToSort.insertionSort(ordering))
  println(listToSort.mergeSort(ordering))
  println((3 :: RNil).mergeSort(ordering))


}
