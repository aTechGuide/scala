package in.kamranali.algorithms.tree

/**
  * https://www.interviewbit.com/problems/2sum-binary-tree/
  */

class TwoSumBinaryTree {

  import TwoSumBinaryTree._

  def t2Sum(A: TreeNode, B: Int): Int  = {

    case class Nodes(var values: Set[Int])

    def inorder(A: TreeNode, nodes: Nodes, B: Int): Boolean = {

      if (A == null) false
      else {

        val result = B - A.value
        if (nodes.values.contains(result)) true
        else {
          nodes.values += A.value
          inorder(A.left, nodes, B) || inorder(A.right, nodes, B)

        }
      }
    }

    if (inorder(A, Nodes(Set.empty[Int]), B)) 1
    else 0
  }

}

object TwoSumBinaryTree extends App {

  case class TreeNode(value: Int, var left: TreeNode = null, var right: TreeNode = null)
  val sol = new TwoSumBinaryTree()

  // Test 1

  var A = TreeNode(7)
  A.left = TreeNode(-1)
  A.right = TreeNode(20)


  var data = sol.t2Sum(A, 13)
  assert(data == 0)

  // Test 2

  A = TreeNode(10)
  A.left = TreeNode(9)
  A.right = TreeNode(20)

  data = sol.t2Sum(A, 40)
  assert(data == 0)

}
