package in.kamranali.algorithms.tree


/**
  * https://www.interviewbit.com/problems/inorder-traversal-of-cartesian-tree/
  */

class InorderTraversalofCartesianTree {

  import InorderTraversalofCartesianTree._

  def buildTree(A: Array[Int]): TreeNode  = {

    if (A.isEmpty) null
    else if (A.length == 1) TreeNode(A(0))
    else {
      val split = A.indexOf(A.max)

      val left = A.slice(0, split)
      val right = A.slice(split + 1, A.length)

      val root = TreeNode(A(split))
      root.left = buildTree(left)
      root.right = buildTree(right)

      root
    }
  }

}

object InorderTraversalofCartesianTree extends App {
  val sol = new InorderTraversalofCartesianTree()

  case class TreeNode(value: Int, var left: TreeNode = null, var right: TreeNode = null)

  // Test 1
  var A = Array[Int](2, 1, 3)
  var data = sol.buildTree(A)

  println(data)
//s  assert(data == 42)
//
//  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.solve(A)
//  assert(data == 42)

}
