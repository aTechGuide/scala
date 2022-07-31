package in.kamranali.algorithms.advance

/*

Examples
- https://leetcode.com/problems/range-sum-query-mutable/
*/
class SegmentTree(input: Array[Int]) {

  private val n = input.length
  private val segment = Array.ofDim[Int](4 * n)

  private def leftChild(i: Int): Int = 2 * i + 1
  private def rightChild(i: Int): Int = 2 * i + 2
  private def calcMid(low: Int, high: Int) = ((high - low) >> 1) + low

  /*
    Build is kind of post Order Traversal
    - We are building the child nodes first and then we build the parent
  */
  private def build(idxS: Int, low: Int, high: Int): Unit = {
    if (high == low) segment(idxS) = input(low)
    else {
      // Step 1: First compute left child and right child
      val mid = calcMid(low, high)

      val lc = leftChild(idxS)
      val rc = rightChild(idxS)

      build(lc, low, mid)
      build(rc, mid + 1, high)

      // Step 2: Compute the current index
      segment(idxS) = segment(lc) + segment(rc)
    }
  }

  // build the Segment Tree
  build(0, 0, n - 1)

  // Public Methods
  def query(l: Int, r: Int): Int = {

    def queryUtil(idxS: Int, low: Int, high: Int): Int = {
      if (low > r || high < l) Int.MaxValue // No Overlap
      else if (low >= l && high <= r) segment(idxS) // Complete Overlap
      else {
        val mid = calcMid(low, high)
        val left = queryUtil(leftChild(idxS), low, mid)
        val right = queryUtil(rightChild(idxS), mid + 1, high)

        math.min(left, right)
      }
    }

    queryUtil(0, 0, n - 1)
  }

  def update(i: Int, value: Int): Unit = {

    def updateUtil(idxS: Int, low: Int, high: Int): Unit = {
      if (low == high) segment(idxS) = value // update the leaf
      else {
        val mid = calcMid(low, high)

        if (i <= mid) updateUtil(leftChild(idxS), low, mid)
        else updateUtil(rightChild(idxS), mid + 1, high)

        // once leaf is updated, Continue updating the parent
        segment(idxS) = math.min(segment(leftChild(idxS)), segment(rightChild(idxS)))
      }
    }

    updateUtil(0, 0, n - 1)
  }

}
