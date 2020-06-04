package in.kamranali.algorithms.tree

/**
  * https://www.interviewbit.com/problems/root-to-leaf-paths-with-sum/
  */

class RoottoLeafPathsWithSum {

  import RoottoLeafPathsWithSum._

  def pathSum(A: TreeNode, B: Int): Array[Array[Int]]  = {

    case class Res(var value: Array[Array[Int]])
    case class Curr(var value: Array[Int])

    def path(A: TreeNode, B: Int, current: Curr, res: Res): Unit = {

      if (A == null) return
      else current.value = current.value :+ A.value

      if (A.left == null && A.right == null) {
        if (current.value.sum == B) {

          res.value = res.value :+ current.value
        }
      }

      if (A.left != null) path(A.left, B, current, res)
      if (A.right != null) path(A.right, B, current, res)

      current.value = current.value.dropRight(1)
    }

    val curr = Curr(Array[Int]())
    val res = Res(Array[Array[Int]]())

    path(A, B, curr, res)

    res.value.foreach(path => println(path.mkString(" ")))

    res.value
  }

}

object RoottoLeafPathsWithSum extends App {
  val sol = new RoottoLeafPathsWithSum()

  case class TreeNode(value: Int, var left: TreeNode = null, var right: TreeNode = null)

  // Test 1
  val root = TreeNode(5)
  root.left = TreeNode(4)
  root.right = TreeNode(8)

  root.left.left = TreeNode(11)
  root.left.left.left = TreeNode(7)
  root.left.left.right = TreeNode(2)

  root.right.left = TreeNode(13)
  root.right.right = TreeNode(4)

  root.right.right.left = TreeNode(5)
  root.right.right.right = TreeNode(1)

  var data = sol.pathSum(root, 22)

}
