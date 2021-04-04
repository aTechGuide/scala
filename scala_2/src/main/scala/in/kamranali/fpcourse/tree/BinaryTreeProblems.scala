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

  // Compare the shape of two Trees
  /*
        _____1_____                     _____8_____
       /           \                   /           \
     __2__       __6__       ~~      __9__       __2__
    /     \     /     \             /     \     /     \
    3     4     7     8             1     3     2     7
           \                               \
            5                               4
  */
  def sameShapeAs[S >: T](that: BTree[S]): Boolean

  // tree is symmetrical w.r.t root Node
  /*
        _____1_____
       /           \
     __2__       __6__
    /     \     /     \
    3     4     7      8

    Tree is symmetrical w.r.t 1 (root Node)
   */
  def isSymmetrical: Boolean
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
  override def sameShapeAs[S >: Nothing](that: BTree[S]): Boolean = that.isEmpty
  override def isSymmetrical: Boolean = true
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

  override def sameShapeAs[S >: T](that: BTree[S]): Boolean = {

    /*
        _____1_____                     _____8_____
       /           \                   /           \
     __2__       __6__       ~~      __9__       __2__
    /     \     /     \             /     \     /     \
    3     4     7     8             1     3     2     7
           \                               \
            5                               4
        sst([1], [8]) =
        sst([2,6], [9,2]) =
        sst([3,4,6], [1,3,2]) =
        sst([4,6],[3,2]) =
        sst([End, 5, 6], [End, 4, 2]) =
        sst([5,6], [4,2]) =
        sst([6], [2]) =
        sst([7,8], [2,7]) =
        sst([8], [7]) =
        sst([], []) =
        true

        Complexity: O(max(N1, N2))
     */
    @tailrec
    def sameShapeTailrec(todoThis: List[BTree[S]], todoThat: List[BTree[S]]): Boolean = {

      if (todoThis.isEmpty) todoThat.isEmpty
      else if (todoThat.isEmpty) todoThis.isEmpty
      else {
        val thisNode = todoThis.head
        val thatNode = todoThat.head

        if (thisNode.isEmpty) thatNode.isEmpty && sameShapeTailrec(todoThis.tail, todoThat.tail)
        else if(thisNode.isLeaf) thatNode.isLeaf && sameShapeTailrec(todoThis.tail, todoThat.tail)
        else sameShapeTailrec(
          thisNode.left :: thisNode.right :: todoThis.tail,
          thatNode.left :: thatNode.right :: todoThat.tail
        )
      }
    }

    sameShapeTailrec(List(this), List(that))
  }

  override def isSymmetrical: Boolean = sameShapeAs(this.mirror)


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
  val tree10x = BNode(10,
    BNode(20,
      BNode(30, BEnd, BEnd),
      BNode(40,
        BEnd,
        BNode(50, BEnd, BEnd),
      )
    ),
    BNode(60,
      BNode(70, BEnd, BEnd),
      BNode(80, BEnd, BEnd)
    )
  )
  val tree10xExtra = BNode(10,
    BNode(20,
      BNode(30, BEnd, BEnd),
      BNode(40,
        BEnd,
        BEnd
      )
    ),
    BNode(60,
      BNode(70, BEnd, BEnd),
      BNode(80, BEnd, BEnd)
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
  assert(tree.sameShapeAs(tree10x))
  assert(!tree.sameShapeAs(tree10xExtra))

  assert(tree10xExtra.isSymmetrical)


}
