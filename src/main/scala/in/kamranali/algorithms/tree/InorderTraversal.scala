package in.kamranali.algorithms.tree

import scala.collection.mutable

/**
  * https://www.interviewbit.com/problems/inorder-traversal/
  */

class InorderTraversal {
  import InorderTraversal._

  def inorder(A: TreeNode): Seq[Int] = {

    val st = mutable.Stack[TreeNode]()
    var iot = List.empty[Int]

    var curr: TreeNode = A

    // Got to left
    while (curr != null) {
      st.push(curr)
      curr = curr.left
    }

    // Iterate till stack is empty
    while (st.nonEmpty) {
      val elem = st.pop
      iot = iot :+ elem.value

      // If there is a right child handle it accordingly
      if (elem.right != null) {
        var temp = elem.right

        while (temp != null) {
          st.push(temp)
          temp = temp.left
        }
      }
    }

    iot
  }

}

object InorderTraversal extends App {

  case class TreeNode(value:Int, var left: TreeNode = null, var right: TreeNode = null)
  val sol = new InorderTraversal()

  // Test 1
  var A = TreeNode(5)
  A.left = TreeNode(2)
  A.right = TreeNode(8)

  A.right.left = TreeNode(7)
  A.right.right = TreeNode(9)

  val iot = sol.inorder(A)
  println(iot)

}
