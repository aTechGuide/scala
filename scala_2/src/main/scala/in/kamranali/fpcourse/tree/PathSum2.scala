package in.kamranali.fpcourse.tree

import scala.annotation.tailrec

object PathSum2 {

  /*
            _____1_____
           /           \
         __2__       __6__
        /     \     /     \
        3     4     7     8
               \
                5
   */

  // all the paths from root to leaf such that the sum of values == target
  def findSumPaths(tree: BTree[Int], target: Int): List[List[Int]] = {

    def stackPaths(tree: BTree[Int], currentTarget: Int): List[List[Int]] = {

      /*
            _____1_____
           /           \
         __2__       __6__
        /     \     /     \
        3     4     7     8
               \
                -1
       sp(1, 6) = [2 6].flatMap(f) = [[2 3] [2 4 -1]].map(path => 1 :: path) = [[1 2 3] [1 2 4 -1]]
         sp(2, 5) = [3, 4].flatMap(f) == [[3]].map(path => 2 :: path) ++ [[4 -1]].map(path => 2 :: path) = [[2 3] [2 4 -1]]
           sp(3, 3) = [[3]]
           sp(4, 3) = [[-1]].map(path => 4 :: path) = [[4, -1]]
             sp(-1, -1) = [[-1]]
         sp(6, 5) = []
     */

      if (tree.isEmpty) List()
      else if (tree.isLeaf)
        if (currentTarget == tree.value) List(List(tree.value))
        else List()

      else List(tree.left, tree.right).filter(! _.isEmpty).flatMap { childNode =>
        val subPaths = stackPaths(childNode, currentTarget - tree.value)
        subPaths.map(path => tree.value :: path)
      }
    }

    @tailrec
    // Complexity: O(N) time, O(N) space
    def tailPaths(nodes: List[BTree[Int]],
                  targets: List[Int],
                  currentPath: List[BTree[Int]],
                  expanded: Set[BTree[Int]],
                  acc: List[List[Int]]): List[List[Int]] = {

      /*
            _____1_____
           /           \
         __2__       __6__
        /     \     /     \
        3     4     7     8
               \
                -1
        tp([1], [6], [], [], []) =
        tp([2 6 1], [5 5 6], [1], [1], []) =
        tp([3 4 2 6 1], [3 3 5 5 6], [2 1], [1 2], []) =
        tp([4 2 6 1], [3 5 5 6], [2 1], [1 2], [[1 2 3]]) =
        tp([-1 4 2 6 1], [-1 3 5 5 6], [4 2 1], [1 2 4], [[1 2 3]]) =
        tp([4 2 6 1], [3 5 5 6], [4 2 1], [1 2 4], [[1 2 4 -1] [1 2 3]]) =
        tp([2 6 1], [5 5 6], [2 1], [1 2 4], [[1 2 4 -1] [1 2 3]]) =
        tp([6 1], [5 6], [1], [1 2 4], [[1 2 4 -1] [1 2 3]])
        ...
        tp([], [], [], [1 2 4], [[1 2 4 -1] [1 2 3]]) =
        [[1 2 4 -1] [1 2 3]]

      Complexity: O(N) time, O(N) space
     */

      if (nodes.isEmpty) acc
      else {
        val node = nodes.head
        val currentTarget = targets.head
        val children = List(node.left, node.right).filter(! _.isEmpty)
        val childrenTargets = children.map(_ => currentTarget - node.value)

        if (node.isLeaf)
          if(node.value == currentTarget)
            tailPaths(nodes.tail, targets.tail, currentPath, expanded, (node :: currentPath).reverse.map(_.value) :: acc)
          else
            tailPaths(nodes.tail, targets.tail, currentPath, expanded, acc)
        else
          if (expanded.contains(node))
            tailPaths(nodes.tail, targets.tail, currentPath.tail, expanded, acc)
          else
            tailPaths(children ++ nodes, childrenTargets ++ targets, node :: currentPath, expanded + node, acc)

      }
    }

//    stackPaths(tree, target)
    tailPaths(List(tree), List(target), List(), Set(), List())

  }


  def main(args: Array[String]): Unit = {
    val tree = BNode(1,
      BNode(2,
        BNode(3, BEnd, BEnd),
        BNode(4,
          BEnd,
          BNode(-1, BEnd, BEnd),
        )
      ),
      BNode(6,
        BNode(7, BEnd, BEnd),
        BNode(8, BEnd, BEnd)
      )
    )

    println(findSumPaths(tree, 6)) // List(List(1, 2, 3), List(1, 2, 4, -1))
    println(findSumPaths(tree, 7)) // List()

  }

}
