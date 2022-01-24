package in.kamranali.algorithms.graph

/*
Union by Rank and Path Compression Approach
TC -> O(m 𝛼(n)) [From Tushar's Video https://www.youtube.com/watch?v=ID00PMy0-vE]
  - For M Operations and N Elements, Proof in clrs book
  - 𝛼(n) is a very slowly growing function and for all practical purposes 𝛼(n) <= 4
  - Hence, O(m 𝛼(n)) =~ O(4m) = O(m)

- https://www.geeksforgeeks.org/disjoint-set-data-structures/
- https://www.youtube.com/watch?v=3gbO7FDYNFQ

Example Leetcode Question -> https://leetcode.com/problems/number-of-provinces/submissions/
*/

/*
References
1 https://courses.cs.washington.edu/courses/cse332/16sp/lectures/Lecture25/25_ho.pdf
2 https://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-046j-design-and-analysis-of-algorithms-spring-2012/lecture-notes/MIT6_046JS12_lec16.pdf

*/
class DisjointUnionSets(n: Int) {

  val parent: Array[Int] = Array.ofDim[Int](n)
  val rank: Array[Int] = Array.ofDim[Int](n)
  var disconnectedCount: Int = n

  private def initializeParent(): Unit = {
    parent.indices.foreach { idx => {
      parent(idx) = idx
    }}
  }

  // Initialization
  initializeParent()

  def findParent(node: Int): Int = {

    // If node is NOT parent of itself, then find parent + do Compression
    if (parent(node) != node) {
      parent(node) = findParent(parent(node))
    }

    parent(node)
  }

  def union(x: Int, y: Int): Boolean = {

    val xRoot = findParent(x)
    val yRoot = findParent(y)

    if (xRoot == yRoot) false
    else if (rank(xRoot) < rank(yRoot)) { // Then move x under y  so that depth of tree remains less
      parent(xRoot) = yRoot
      disconnectedCount = disconnectedCount - 1
      true
    }
    else if (rank(yRoot) < rank(xRoot)) { // Else if y's rank is less than x's rank
      // Then move y under x so that depth of tree remains less
      parent(yRoot) = xRoot
      disconnectedCount = disconnectedCount - 1
      true
    } else { // if ranks are the same
      // Then move y under x (doesn't matter which one goes where)
      parent(yRoot) = xRoot;

      // And increment the result tree's rank by 1
      rank(xRoot) = rank(xRoot) + 1;
      disconnectedCount = disconnectedCount - 1
      true
    }
  }
}

object DisjointUnionSetsTest {
  def main(args: Array[String]): Unit = {
    // Let there be 5 persons with ids as 0, 1, 2, 3 and 4
    val n = 5
    val dus = new DisjointUnionSets(n)

    // 0 is a friend of 2
    dus.union(0, 2)

    // 4 is a friend of 2
    dus.union(4, 2)

    // 3 is a friend of 1
    dus.union(3, 1)

    // Check if 4 is a friend of 0
    if (dus.findParent(4) == dus.findParent(0)) println("Yes")
    else println("No")

    // Check if 1 is a friend of 0
    if (dus.findParent(1) == dus.findParent(0)) println("Yes")
    else println("No")
  }
}
