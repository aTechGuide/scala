package in.kamranali.lm.binarytree

object BinaryTreeToDoublyLinkedList {
  class TreeNode(value: Int, var l: TreeNode = null, var r: TreeNode = null)
  case class NodeRef(var ref: TreeNode = null)

  def convert(root: TreeNode): TreeNode = {

    // As we have to do it in place, Nothing will return
    def inorder(node: TreeNode, prev: NodeRef, head: NodeRef): Unit = {

      if (node == null) ()
      else {
        inorder(node.l, prev, head)

        // do the pointer shifting
        if (prev.ref == null) {
         // for the first node we set the parent
          prev.ref = node
          head.ref = node
        }
        else {
          // for rest of the nodes we update the pointers
          prev.ref.r = node
          node.l = prev.ref

          prev.ref = node
        }
        inorder(node.r, prev, head)
      }
    }

    val headRef = NodeRef()
    inorder(root, NodeRef(), headRef)

    headRef.ref
  }

  def main(args: Array[String]): Unit = {
    val r = new TreeNode(1, new TreeNode(2), new TreeNode(3))
    val head = convert(r)
  }

}
