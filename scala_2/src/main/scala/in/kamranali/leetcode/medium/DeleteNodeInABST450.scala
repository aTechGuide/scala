package in.kamranali.leetcode.medium

object DeleteNodeInABST450 {
  case class TreeNode(var value: Int, var left: TreeNode = null, var right: TreeNode = null)
  def deleteNode(root: TreeNode, key: Int): TreeNode = {
    
    def inOrderSuccessor(node: TreeNode): TreeNode = {
      if (node.left == null) node
      else inOrderSuccessor(node.left)
    }
    
    def delete(node: TreeNode, k: Int): TreeNode = {
      if (node == null) node
      else if (k < node.value) {
        // search left
        node.left = delete(node.left, k)
        
        node
      } else if (k > node.value) {
        // search right
        node.right = delete(node.right, k)
        
        node
      } else {
        // Found node to be deleted
        
        // Case 1
        if (node.left == null && node.right == null) {
          null
        } else if (node.left == null) {
          // right child is present
          node.right
        } else if (node.right == null) {
          // left child is present
          node.left
        } else {
          // both child are present
          val minRight = inOrderSuccessor(node.right)
          
          node.value = minRight.value
          
          node.right = delete(node.right, minRight.value)
          
          node
        }
      }
    }

   delete(root, key)
  }

  def main(args: Array[String]): Unit = {
    val root = TreeNode(5, TreeNode(3, TreeNode(2), TreeNode(4)), TreeNode(6, null, TreeNode(7)))
    println(deleteNode(root, 5))
  }
}