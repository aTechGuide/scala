package in.kamranali.fpcourse.tree

import scala.annotation.tailrec
import scala.collection.immutable.Queue

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

  // collect all nodes to a list
  def toList: List[T]
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
  override def toList: List[Nothing] = List()
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

  override def toList: List[T] = {
    /*
            _____1_____
           /           \
         __2__       __6__
        /     \     /     \
        3     4     7     8
               \
                5
    Options:
    - pre-order: [1 2 3 4 5 6 7 8]
    - in-order: [3 2 4 5 1 7 6 8]
    - post-order: [3 5 4 2 7 6 8 1]
    - per-level: [1 2 6 3 4 7 8 5]
   */

    def preorderStack(node: BTree[T]): List[T] = {
      if (node.isEmpty) List()
      else {
        val left = preorderStack(node.left)
        val right = preorderStack(node.right)
        node.value :: left ++ right
      }
    }

    def preorderStackDaniel(node: BTree[T]): List[T] = {
      if (node.isEmpty) List()
      else node.value :: preorderStackDaniel(node.left) ++ preorderStackDaniel(node.right)
    }

    @tailrec
    def preorderTailrec(stack: List[BTree[T]], visited: Set[BTree[T]]= Set(), acc: Queue[T] = Queue()): List[T] = {

      /*
      pot([1], [], []) =
      pot([1 2 6], [1], []) =
      pot([2 6], [1], [1]) =
      pot([2 3 4 6], [1 2], [1]) =
      pot([3 4 6], [1 2], [1 2]) =
      pot([4 6], [1 2], [1 2 3] =
      pot([4 5 6], [1 2 4], [1 2 3]) =
      pot([5 6], [1 2 4], [1 2 3 4]) =
      pot([6], [1 2 4], [1 2 3 4 5]) =
      pot([6 7 8], [1 2 4 6], [1 2 3 4 5]) =
      pot([7 8], [1 2 4 6], [1 2 3 4 5 6]) =
      pot([8], [1 2 4 6], [1 2 3 4 5 6 7]) =
      pot([], [1 2 4 6], [1 2 3 4 5 6 7 8]) =
      [1 2 3 4 5 6 7 8]
     */

      if (stack.isEmpty) acc.toList
      else if (stack.head.isEmpty) preorderTailrec(stack.tail, visited, acc)
      else if (stack.head.isLeaf) preorderTailrec(stack.tail, visited, acc.enqueue(stack.head.value))
      else if (visited.contains(stack.head)) preorderTailrec(stack.tail, visited, acc.enqueue(stack.head.value))
      else {
        val node = stack.head
        val newStack = node :: node.left :: node.right :: stack.tail

        preorderTailrec(newStack, visited + node, acc)
      }
    }

    @tailrec
    def inorderTailrec(stack: List[BTree[T]], visited: Set[BTree[T]]= Set(), acc: Queue[T] = Queue()): List[T] = {

      if (stack.isEmpty) acc.toList
      else if (stack.head.isEmpty) inorderTailrec(stack.tail, visited, acc)
      else if (stack.head.isLeaf) inorderTailrec(stack.tail, visited, acc.enqueue(stack.head.value))
      else if (visited.contains(stack.head)) inorderTailrec(stack.tail, visited, acc.enqueue(stack.head.value))
      else {
        val node = stack.head
        val newStack = node.left :: node :: node.right :: stack.tail

        inorderTailrec(newStack, visited + node, acc)
      }
    }

    @tailrec
    def postorderTailrec(stack: List[BTree[T]], visited: Set[BTree[T]]= Set(), acc: Queue[T] = Queue()): List[T] = {

      if (stack.isEmpty) acc.toList
      else if (stack.head.isEmpty) postorderTailrec(stack.tail, visited, acc)
      else if (stack.head.isLeaf) postorderTailrec(stack.tail, visited, acc.enqueue(stack.head.value))
      else if (visited.contains(stack.head)) postorderTailrec(stack.tail, visited, acc.enqueue(stack.head.value))
      else {
        val node = stack.head
        val newStack = node.left :: node.right :: node :: stack.tail

        postorderTailrec(newStack, visited + node, acc)
      }
    }

    @tailrec
    def perlevelTailrec(level: List[BTree[T]], acc: Queue[T] = Queue()): List[T] = {

      /*
        plt([1], []) =
        plt([2, 6], [1]) =
        plt([3,4,7,8], [1 2 6]) =
        plt([5], [1 2 6 3 4 7 8]) =
        plt([], [1 2 6 3 4 7 8 5]) =
        [1 2 6 3 4 7 8 5]
     */

      if (level.isEmpty) acc.toList
      else if (level.head.isEmpty) perlevelTailrec(level.tail, acc)
      else if (level.head.isLeaf) perlevelTailrec(level.tail, acc.enqueue(level.head.value))
      else {
        val expanded = level.flatMap( elem => List(elem.left, elem.right))
        val elems = level.map(_.value)

        perlevelTailrec(expanded, acc.enqueueAll(elems))
      }

    }

    // preorderTailrec(List(this))
    // inorderTailrec(List(this))
    // postorderTailrec(List(this))
    perlevelTailrec(List(this))

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

  def testMethods(): Unit = {
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

  println(tree.toList)



}
