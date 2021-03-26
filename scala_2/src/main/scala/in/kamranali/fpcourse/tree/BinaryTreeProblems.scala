package in.kamranali.fpcourse.tree

import scala.annotation.tailrec

sealed abstract class BTree[+T] {
  def value: T
  def left: BTree[T]
  def right: BTree[T]
  def isEmpty: Boolean

  /**
    * Easy
    */
  def isLeaf: Boolean
  def collectLeaves: List[BTree[T]]
  def leafCount: Int

  /**
    * Medium
    */
  // # of nodes in the tree
  def size: Int

  // Collect all the nodes at a given level
  def collectNodes(level: Int): List[BTree[T]]

  // Mirror a tree
  /*
        _____1_____                     _____1_____
       /           \                   /           \
     __2__       __6__       ->      __6__       __2__
    /     \     /     \             /     \     /     \
    3     4     7     8             8     7     4     3
           \                                   /
            5                                 5
   */
  def mirror: BTree[T]
}

case object BEnd extends BTree[Nothing] {
  override def value: Nothing = throw new NoSuchElementException
  override def left: BTree[Nothing] = throw new NoSuchElementException
  override def right: BTree[Nothing] = throw new NoSuchElementException
  override def isEmpty: Boolean = true

  override def isLeaf: Boolean = false
  override def collectLeaves: List[BTree[Nothing]] = List()
  override def leafCount: Int = 0
  override val size: Int = 0
  override def collectNodes(level: Int): List[BTree[Nothing]] = List()
  override def mirror: BTree[Nothing] = BEnd
}

case class BNode[+T](override val value: T, override val left: BTree[T], override val right: BTree[T]) extends BTree[T] {
  override def isEmpty: Boolean = false

  override def isLeaf: Boolean = left.isEmpty && right.isEmpty

  override def collectLeaves: List[BTree[T]] = {
    @tailrec
    def collectLeavesTailRec(todo: List[BTree[T]], leaves: List[BTree[T]]): List[BTree[T]] = {
      if (todo.isEmpty) leaves
      else if (todo.head.isEmpty)
        collectLeavesTailRec(todo.tail, leaves)
      else if (todo.head.isLeaf)
        collectLeavesTailRec(todo.tail, todo.head :: leaves)
      else {
        val node = todo.head
        collectLeavesTailRec(node.left :: node.right :: todo.tail, leaves)
      }
    }

    collectLeavesTailRec(List(this), List.empty[BNode[T]])
  }

  override def leafCount: Int = collectLeaves.length

  override val size: Int = 1 + left.size + right.size

  override def collectNodes(level: Int): List[BTree[T]] = {
    @tailrec
    def collectNodesTailrec(currentLevel: Int, currNodes: List[BTree[T]]): List[BTree[T]] = {
      if (currNodes.isEmpty) List()
      else if (currentLevel == level) currNodes
      else {
        val expanded = for {
          node <- currNodes
          child <- List(node.left, node.right) if !child.isEmpty
        } yield child

        collectNodesTailrec(currentLevel + 1, expanded)
      }
    }

    if (level < 0) List()
    else collectNodesTailrec(0, List(this))

  }

  override def mirror: BTree[T] = {
//    def mirrorPostOrder(original: BTree[T], mirror: BTree[T]): BTree[T] = {
//      if (original.isEmpty) BEnd
//      else {
//        val left = mirrorPostOrder(original.right, mirror)
//        val right = mirrorPostOrder(original.left, mirror)
//
//        BNode(original.value, left, right)
//      }
//    }
//    mirrorPostOrder(this, BEnd)

    /*
          _____1_____                     _____1_____
         /           \                   /           \
       __2__       __6__       ->      __6__       __2__
      /     \     /     \             /     \     /     \
      3     4     7     8             8     7     4     3
             \                                   /
              5                                 5
      mt([1], [], []) =
      mt([2,6,1], [1], []) =
      mt([3,4,2,6,1], [1,2], []) =
      mt([4,2,6,1], [1,2], [3]) =
      mt([End, 5, 4,2,6,1], [1,2,4], [3]) =
      mt([5,4,2,6,1], [1,2,4], [End, 3]) =
      mt([4,2,6,1], [1,2,4], [5, End, 3]) =
      mt([2,6,1], [1,2,4], [(4 5 End), 3]) =
      mt([6,1], [1,2,4], [(2 (4 5 End) 3)] =
      mt([7,8,6,1], [1,2,4,6], [(2 (4 5 End) 3)]) =
      mt([8,6,1], [1,2,4,6], [7, (2 (4 5 End) 3)]) =
      mt([6,1], [1,2,4,6], [8,7, (2 (4 5 End) 3)]) =
      mt([1], [1,2,4,6], [(6 8 7), (2 (4 5 End) 3)]) =
      mt([], [1,2,4,6], [(1 (6 8 7) (2 (4 5 End) 3)]) =
      (1 (6 8 7) (2 (4 5 End) 3)

      Complexity: O(N)
     */
    @tailrec
    def mirrorTailrec(todo: List[BTree[T]], expanded: Set[BTree[T]], done: List[BTree[T]]): BTree[T] = {
      if (todo.isEmpty) done.head
      else if (todo.head.isEmpty || todo.head.isLeaf)
        mirrorTailrec(todo.tail, expanded, todo.head :: done)
      else if (expanded.contains(todo.head)) {
        val node = BNode(todo.head.value, done.head, done.tail.head)
        mirrorTailrec(todo.tail, expanded, node :: done.tail.tail)
      } else {
        val newTodo = todo.head.left :: todo.head.right :: todo
        mirrorTailrec(newTodo, expanded + todo.head, done)
      }
    }

    mirrorTailrec(List(this), Set(), List())
  }


}
object BinaryTreeProblems extends App {

  val tree = BNode(1,
    BNode(2,
      BNode(3, BEnd, BEnd),
      BNode(4,
        BEnd,
        BNode(5, BEnd, BEnd),
      )
    ),
    BNode(6,
      BNode(7, BEnd, BEnd),
      BNode(8, BEnd, BEnd)
    )
  )

  val degenerate = (1 to 100000).foldLeft[BTree[Int]](BEnd)((tree, number) => {
    BNode(number, tree, BEnd)
  })

  println(tree.collectLeaves.map(_.value))
  println(tree.leafCount)
  println(tree.size)
  println(degenerate.size)
  println(tree.collectNodes(2).map(_.value))
  println(tree.mirror)


}
