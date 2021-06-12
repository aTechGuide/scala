package in.kamranali.fpcourse.tree

import scala.annotation.tailrec
import scala.collection.immutable.Queue

object PathSum {

  /*
            _____1_____
           /           \
         __2__       __6__
        /     \     /     \
        3     4     7     8
               \
                5
        tree, 6 => true
        tree, 7 => false
   */
  // Return true if there is a path from root to a leaf, such that the sum of values is target.
  def hasPathSum(tree: BTree[Int], target: Int): Boolean = {

    def isSum(node: BTree[Int], sum: Int): Boolean = {
      if (node.isLeaf) if (sum + node.value == target) true else false
      else if (node.isEmpty) sum == target
      else isSum(node.left, sum + node.value) || isSum(node.right, sum + node.value)
    }

    // Daniels Version
    @tailrec
    def hasPathSumStack(tree: BTree[Int], target: Int): Boolean =
      if (tree.isEmpty) target == 0
      else if (tree.isLeaf) target == tree.value
      else if (tree.left.isEmpty) hasPathSumStack(tree.right, target - tree.value)
      else hasPathSumStack(tree.left, target - tree.value)

    // Complexity: O(N) time, O(N) memory
    def hasPathSumTailrec(nodes: Queue[BTree[Int]], targets: Queue[Int]): Boolean = {

      /*
        hp([1], [6]) =
        hp([2 6], [5 5]) =
        hp([6 3 4], [5 3 3]) =
        hp([3 4 7 8], [3 3 -1 -1]) =
        true
        Complexity: O(N) time, O(N) memory
     */

      if (nodes.isEmpty) false
      else {
        val node = nodes.head
        val targetValue = targets.head
        val children = List(node.left, node.right).filter(! _.isEmpty)
        val childrenTarget = children.map(_ => targetValue - node.value)

        if (node.isLeaf && node.value == targetValue) true
        else hasPathSumTailrec(nodes.tail ++ children, targets.tail ++ childrenTarget)
      }
    }

    // isSum(tree, 0)
    // hasPathSumStack(tree, target)
    hasPathSumTailrec(Queue(tree), Queue(target))

  }

  def main(args: Array[String]): Unit = {
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

    println(hasPathSum(tree, 6))
    println(hasPathSum(tree, 7))

  }

}
