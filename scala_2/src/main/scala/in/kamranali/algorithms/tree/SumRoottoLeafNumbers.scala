package in.kamranali.algorithms.tree

/**
  * https://www.interviewbit.com/problems/sum-root-to-leaf-numbers/
  */

class SumRoottoLeafNumbers {

  import SumRoottoLeafNumbers._

  def sumNumbers(A: TreeNode): Int  = {

    case class Total(var value: Int)
    def sumUtil(A: TreeNode, sum: Int, total: Total): Unit = {

      if (A == null) return

      val currentTotal = (sum*10 + A.value) % 1003
      if (A.left == null && A.right == null) {
        total.value += currentTotal
        total.value %= 1003
      }

      if (A.left != null) sumUtil(A.left, currentTotal, total)
      if (A.right != null) sumUtil(A.right, currentTotal, total)

    }

    val total: Total = Total(0)

    sumUtil(A, 0, total)

    println(total.value)
    println(total.value % 1003)
    total.value
  }

}

object SumRoottoLeafNumbers extends App {

  case class TreeNode(value: Int, var left: TreeNode = null, var right: TreeNode = null)

  val sol = new SumRoottoLeafNumbers()

  // Test 1
  var A = TreeNode(1)
  A.left = TreeNode(2)
  A.right = TreeNode(3)

  A.right.left = TreeNode(4)
  A.right.left.right = TreeNode(5)


  var data = sol.sumNumbers(A)
  assert(data == 354)

  // Test 2
  A = TreeNode(1)
  data = sol.sumNumbers(A)
  assert(data == 1)

}
