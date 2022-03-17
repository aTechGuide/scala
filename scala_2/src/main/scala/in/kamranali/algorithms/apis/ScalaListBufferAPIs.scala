package in.kamranali.algorithms.apis

/*

Ref -> https://stackoverflow.com/questions/11049213/which-scala-mutable-list-to-use

[DoubleLinkedList] is a linked list which allows you to traverse back-and-forth through the list of nodes.
Use its prev and next references to go to the previous or the next node, respectively.

[LinkedList] is a singly linked list, so there are not prev pointers -
if you only traverse to the next element of the list all the time, this is what you need.

EDIT: Note that the two above are meant to be used internally as building blocks for more complicated list structures
like MutableLists which support efficient append, and mutable.Queues.

The two collections above both have linear-time append operations.

[ListBuffer] is a buffer class.
Although it is backed by a singly linked list data structure, it does not expose the next pointer to the client,
so you can only traverse it using iterators and the foreach.
Its main use is, however, as a buffer and an immutable list builder
- you append elements to it via +=, and when you call result, you very efficiently get back a functional immutable.List.
Unlike mutable and immutable lists, both append and prepend operations are constant-time - you can append at the end via += very efficiently.

[MutableList] is used internally, you usually do not use it unless you plan to implement a custom collection class based
on the singly linked list data structure. Mutable queues, for example, inherit this class.
MutableList class also has an efficient constant-time append operation, because it maintains a reference to the last node in the list.
*/
object ScalaListBufferAPIs extends App {
  import scala.collection.mutable

  val lb = mutable.ListBuffer[Int]()

  lb.append(1)
  println(lb) // ListBuffer(1)

  lb += 2 // append
  println(lb) // ListBuffer(1, 2)

  lb.append(3)
  println(lb) // ListBuffer(1, 2, 3)

  lb.prepend(4)
  println(lb) // ListBuffer(4, 1, 2, 3)

  lb.remove(0)
  println(lb) // ListBuffer(1, 2, 3)

  lb.result()

}
