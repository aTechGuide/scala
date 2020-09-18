package in.kamranali.collections

/**
  * Basic Scala Lesson 29 [Collections Overview]
  *
  * In Notes of One Note
  * - Scala > Concepts
  *
  *
  * Ref
  * - https://www.udemy.com/course/rock-the-jvm-scala-for-beginners/learn/lecture/7660714
  */

object CollectionBasics {

  /*
  Scala offers both Mutable and Immutable collections

  Mutable: Collections that can be updated in place (present in Mutable Package)
  Immutable: They never Change. So it instantiated new collection when making a change (present in Immutable Package)

  Immutable(IM)/Mutable(M) Hierarchy

            Traversable(M + IM)

             Iterable(M + IM)

  Set(M + IM)   Seq(M + IM)   Map(M + IM)

         Set
  HashSet(M + IM)  LinkedHashSet(M)/SortedSet(IM)

         Map
  HashMap(M + IM)  MultiMap(M)/SortedMap(IM)

        Seq
IndexedSeq(M + IM)  Buffer(M) LinearSeq(M + IM)

IndexedSeq(IM) -> Vector, String, Range
LinearSeq(IM) -> List, Stream, Stack, Queue

IndexedSeq -> StringBuilder, ArrayBuffer
Buffer -> ArrayBuffer, ListBuffer
LinearSeq -> LinkedList, MutableList

   */

}
