package in.kamranali.leetcode.medium

object ConstructBinaryTreeFromInorderAndPostorderTraversal106 {
  case class TreeNode(var value: Int, var right: TreeNode = null, var left: TreeNode = null)

  def buildTree(inorder: Array[Int], postorder: Array[Int]): TreeNode = {
    // Map of array item to its index
    val inorderMapping = inorder.zipWithIndex.toMap

    def create(post: Int, l: Int, r: Int): TreeNode = {

      if ( l == r) new TreeNode(postorder(post))
      else if (l > r) null
      else {
        val root = new TreeNode(postorder(post))

        val searchRootIdx = inorderMapping(root.value)
        val newPost = post - (r - searchRootIdx + 1)
        
        // root.right = create(newPost, searchRootIdx + 1, r)
        root.right = create(post - 1, searchRootIdx + 1, r)
        root.left = create(newPost, l, searchRootIdx - 1)

        // skipping all the node on the left branches/subtrees of current node
        root
      }
    }

    val len = inorder.length - 1
    create(len, 0, len)
  }

  def main(args: Array[String]): Unit = {
    println(buildTree(Array(9,3,15,20), Array(9,15,20,3)))
  }
}