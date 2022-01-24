package in.kamranali.algorithms.advance

/*
Range Sum
- https://www.youtube.com/watch?v=rXnXRU8yMF0

*/
class SegmentTreeLazyRangeSum(input: Array[Int]) {

  private val n = input.length
  private val segment = Array.ofDim[Int](4 * n)
  private val lazySegment = Array.ofDim[Int](4 * n)

  private def leftChild(i: Int): Int = 2 * i + 1
  private def rightChild(i: Int): Int = 2 * i + 2
  private def calcMid(low: Int, high: Int) = ((high - low) >> 1) + low

  /*
    Build is kind of post Order Traversal
    - We are building the child nodes first and then we build the parent
  */
  private def build(idxS: Int, low: Int, high: Int): Unit = {
    if (high == low) segment(idxS) = input(low) // leaves are created from actual array
    else {
      val mid = calcMid(low, high)

      build(leftChild(idxS), low, mid) // fill left child
      build(rightChild(idxS), mid + 1, high) // fill right child

      segment(idxS) = segment(leftChild(idxS)) + segment(rightChild(idxS)) // fill parent, as left and right child are done
    }
  }

  // build the Segment Tree
  build(0, 0, n - 1)

  // Public Methods
  def query(l: Int, r: Int): Int = {

    def queryUtil(idxS: Int, low: Int, high: Int): Int = {

      // Case 1: Update previously remaining updates and propagate downwards
      if (lazySegment(idxS) != 0) {
        segment(idxS) = segment(idxS) + (high - low + 1) * lazySegment(idxS)

        // propagate the lazy update downwards, for thee remaining nodes to get updated
        if (low != high) { // we have children
          lazySegment(leftChild(idxS)) = lazySegment(leftChild(idxS)) + lazySegment(idxS)
          lazySegment(rightChild(idxS)) = lazySegment(rightChild(idxS)) + lazySegment(idxS)
        }

        lazySegment(idxS) = 0 // Task of lazy is over
      }

      // Rest is same
      if (r < low || high < l) 0 // No Overlap
      else if (low >= l && high <= r) segment(idxS) // Complete Overlap
      else {
        val mid = calcMid(low, high)
        val left = queryUtil(leftChild(idxS), low, mid)
        val right = queryUtil(rightChild(idxS), mid + 1, high)

        left + right
      }
    }

    queryUtil(0, 0, n - 1)
  }

  def updateRange(l: Int, r: Int, value: Int): Unit = {

    def updateUtil(idxS: Int, low: Int, high: Int): Unit = {
      // Case 1: Update previously remaining updates and propagate downwards
      if (lazySegment(idxS) != 0) {
        segment(idxS) = segment(idxS) + (high - low + 1) * lazySegment(idxS)

        // propagate the lazy update downwards, for thee remaining nodes to get updated
        if (low != high) { // we have children
          lazySegment(leftChild(idxS)) = lazySegment(leftChild(idxS)) + lazySegment(idxS)
          lazySegment(rightChild(idxS)) = lazySegment(rightChild(idxS)) + lazySegment(idxS)
        }

        lazySegment(idxS) = 0 // Task of lazy is over
      }

      // Case 2: No Overlap [low high l r || l r low high]
      if (high < l || r < low) () // Do Nothing
      // Case 3: Complete Overlap [ l low high r]
      else if (low >= l && high <= r) {
        segment(idxS) = segment(idxS) + (high - low + 1) * value

        if (low != high) { // we have children
          lazySegment(leftChild(idxS)) = lazySegment(leftChild(idxS)) + value
          lazySegment(rightChild(idxS)) = lazySegment(rightChild(idxS)) + value

        }
      } else { // Case 4: Partial Overlap
        val mid = calcMid(low, high)

        updateUtil(leftChild(idxS), low, mid)
        updateUtil(rightChild(idxS), mid + 1, high)

        segment(idxS) = segment(leftChild(idxS)) + segment(rightChild(idxS))
      }
    }

    updateUtil(0, 0, n - 1)
  }

}

object SegmentTreeLazyRangeSumTest {
  def main(args: Array[String]): Unit = {
    val st = new SegmentTreeLazyRangeSum(Array(1, 2, 3, 4, 5))

    println(st.query(0, 4)) // 15
    st.updateRange(1, 3, 2)
    println(st.query(0, 4)) // 21
  }
}
