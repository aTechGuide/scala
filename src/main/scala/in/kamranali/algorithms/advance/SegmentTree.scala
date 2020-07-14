package in.kamranali.algorithms.advance

/**
  * @param input
  *
  * References
  * - NextPowerOf2 -> https://github.com/mission-peace/interview/blob/master/src/com/interview/bits/NextPowerOf2.java
  * - youtube.com/watch?v=ZBHKZF5w4YUs
  */
class SegmentTree(input: Array[Int]) {

  private val length = input.length
  private val st = buildSegmentTree(input)

  def buildSegmentTree(input: Array[Int]): Array[Int] = {

    def construct(st: Array[Int], input: Array[Int], left: Int, right: Int, pos: Int): Int = {

      st(pos) =
        if (left == right) input(left)
        else {
          val mid: Int = (right - left) / 2 + left
          val leftChild = construct(st, input, left, mid, 2 * pos + 1) // Passing Positions Left Child
          val rightChild = construct(st, input, mid + 1, right, 2 * pos + 2) // Passing Positions Right Child

          math.min(leftChild, rightChild)
        }

      st(pos)
    }

    // Create an Array of Size = ((Next) power of 2 * 2) - 1
    val sizeOfST = (nextPowerOf2(length) * 2) - 1
    val st = Array.fill[Int](sizeOfST)(Int.MaxValue)

    // Construct Segment Tree
    construct(st, input, 0, length - 1, 0)

    st
  }

  private def nextPowerOf2(num: Int): Int = {

    if (num == 0) 1
    else if ( (num & (num - 1)) == 0) num
    else {
      // Come down to prev power of 2 [e.g. 7 will come down to 4]
      var temp = num
      while ((temp & (temp - 1)) > 1) {
        temp = temp & (temp - 1)
      }

      // Left shift to get Next Power of 2
      temp << 1
    }
  }

  def rangeQuery(qLow: Int, qHigh: Int): Int = {

    def rangeQueryUtil(qLow: Int, qHigh: Int, low: Int, high: Int, pos: Int): Int = {

      // Total Overlap
      if (qLow <= low && qHigh >= high) st(pos)
      // No Overlap
      else if (qLow > high || qHigh < low) Int.MaxValue
      else {

        val mid = (high - low) / 2 + low

        math.min(
          rangeQueryUtil(qLow, qHigh, low, mid, 2*pos + 1),
          rangeQueryUtil(qLow, qHigh, mid + 1, high, 2*pos + 2)
        )
      }

    }

    rangeQueryUtil(qLow, qHigh, 0, length - 1, 0)
  }

  def update(idx: Int, value: Int): Unit = {

    def updateUtil(idx: Int, value: Int, low: Int, high: Int, pos: Int): Int = {

      if (idx < low || idx > high) Int.MaxValue
      else if (low == high) { st(pos) = value; value }
      else {
        val mid = (high - low) / 2 + low
        val leftChild = updateUtil(idx, value, low, mid, 2*pos + 1)
        val rightChild = updateUtil(idx, value, mid + 1, high, 2*pos + 2)

        st(pos) = math.min(leftChild, rightChild)
        st(pos)
      }

    }

    updateUtil(idx, value, 0, length - 1, 0)
  }

  def printSegmentTree(): Unit = println(st.mkString(" "))
}

object SegmentTreeTest extends App {

  val sol = new SegmentTree(Array(-1, 2, 4, 0))
  assert(0 == sol.rangeQuery(1, 3))

  sol.printSegmentTree() // -1 -1 0 -1 2 4 0

  sol.update(1, -2)

  sol.printSegmentTree() // -2 -2 0 -1 -2 4 0
}
