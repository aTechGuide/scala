package in.kamranali.algorithms.tree

case class MaxDepthOfBinaryTree() {

  import MaxDepthOfBinaryTree._

  def maxDepth(A: TreeNode): Int  = {

    if (A == null) return 0

    val left = maxDepth(A.left) + 1
    val right = maxDepth(A.right) + 1

    left max right
  }

}

object MaxDepthOfBinaryTree extends App {

  case class TreeNode(value: Int, var left: TreeNode = null, var right: TreeNode = null)

  // Test 1
  val root = TreeNode(1)
  root.left = TreeNode(2)
  root.right = TreeNode(3)

  assert(2 == MaxDepthOfBinaryTree().maxDepth(root))

}
