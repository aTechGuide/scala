package in.kamranali.algorithms.tree


/**
  * https://www.interviewbit.com/problems/least-common-ancestor/
  */

class LeastCommonAncestor {

  import LeastCommonAncestor._

  def pathTo(n: TreeNode, v: Int):Option[List[Int]] =
    n match {
      case null => None
      case _ if n.value == v => Some(List(n.value))
      case _ => (pathTo(n.left,v) orElse pathTo(n.right,v)).map(n.value :: _)
    }

  def lca(A: TreeNode, B: Int, C: Int): Int = {

    val pathAB = pathTo(A,B)
    val pathAC = pathTo(A,C)

    println(pathAB)
    println(pathAC)

    (pathAB,pathAC) match {
      case (Some(x),Some(y)) =>
        val zipped = x zip y
        val prefix = zipped.takeWhile(tup => tup._1 == tup._2)
        prefix.last._1
      case _ => -1
    }

  }


  def lca_mine(A: TreeNode, B: Int, C: Int): Int  = {

    case class Path(var path: List[Int])

    def search(A: TreeNode, node: Int, path: Path): Boolean = {

      if (A == null) return false

      if (A.value == node) {
        path.path = path.path :+ A.value
        return true
      }

      val left = search(A.left, node, path)
      val right = search(A.right, node, path)

      if (left || right) path.path = path.path :+ A.value

      left || right
    }

    val path1 = Path(List())
    val path2 = Path(List())

    val res1 = search(A, B, path1)
    val res2 = search(A, C, path2)

    val LCA: List[Int] = if (res1 && res2) {

      val intersect = path1.path.intersect(path2.path)
      intersect
    } else {
      List(-1)
    }

    LCA.head
  }

}

object LeastCommonAncestor extends App {

  case class TreeNode(value: Int, var left: TreeNode = null, var right: TreeNode = null)

  val sol = new LeastCommonAncestor()

  // Test 1
  val root = TreeNode(3)
  root.left = TreeNode(5)
  root.right = TreeNode(1)

  root.left.left = TreeNode(6)
  root.left.right = TreeNode(2)

  var data = sol.lca(root, 6, 2)
  assert(data == 5)

  // Test 2
//  val root1 = TreeNode(3)
//  root1.left = TreeNode(5)
//  root1.right = TreeNode(1)
//
//  root1.left.right = TreeNode(2)
//  root1.left.right.right = TreeNode(4)
//
//  data = sol.lca(root1, 5, 4)
//  assert(data == 5)

}
