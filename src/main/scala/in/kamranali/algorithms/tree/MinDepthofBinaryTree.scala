package in.kamranali.algorithms.tree

/**
  * https://www.interviewbit.com/problems/min-depth-of-binary-tree/
  */
class MinDepthofBinaryTree {

  import MinDepthofBinaryTree._

    def minDepth(A: TreeNode): Int  = {

      if(A == null) 0

      // If either of the nodes is absent
      // Current node will be non Null child Node + 1
      // DO NOT do math.min(..) as null child node will have zero value and will tamper the results
      else if(A.left == null) minDepth(A.right) + 1
      else if(A.right == null)minDepth(A.left) + 1
      else {

        // When both left and right nodes are present take Minimum
        val lDepth = minDepth(A.left)
        val rDepth = minDepth(A.right)
        math.min(lDepth, rDepth) + 1
      }
    }
}

object MinDepthofBinaryTree extends App {

  case class TreeNode(value: Int, var left: TreeNode = null, var right: TreeNode = null)

  val sol = new MinDepthofBinaryTree()

  // Test 1
  val root = TreeNode(1)
  root.left = TreeNode(2)
  // root.right = TreeNode(3)

  root.left.left = TreeNode(4)
  root.left.right = TreeNode(5)

  root.left.left.left = TreeNode(6)
  root.left.left.right = TreeNode(7)

  var data = sol.minDepth(root)
  assert(data == 1)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.solve(A)
//  assert(data == 42)

}
