package in.kamranali.leetcode.medium

object SnakesAndLadders909 {
  case class Node(p: Int, move: Int)

  def snakesAndLadders(board: Array[Array[Int]]): Int = {
    import scala.collection.mutable.Queue

    // Initializations
    val n = board.length
    val destination = n * n
    val visited = Array.fill[Boolean](destination + 1)(false)
    val move = Array.fill[Int](destination + 1)(-1)

    // create Queue for BFS
    val q = Queue[Node](Node(1, 0)) // Start with first Node

    // Auxiliary Function
    def populateMove(idx: Int, r: Int, c: Int, isGoingRight: Boolean): Unit = {
      if (r == -1) ()
      else if (c < 0 || c == n) {
        if (isGoingRight) populateMove(idx, r - 1, n - 1, !isGoingRight)
        else populateMove(idx, r - 1, 0, !isGoingRight)
      }
      else if (isGoingRight) {
        move(idx) = board(r)(c)
        populateMove(idx + 1, r, c + 1, isGoingRight)
      } else {
        move(idx) = board(r)(c)
        populateMove(idx + 1, r, c - 1, isGoingRight)
      }
    }

    def bfs(): Int = {
      if (q.isEmpty) -1
      else {
        val n = q.dequeue

        if (n.p == destination) n.move // We have found the solution, break
        else if (visited(n.p)) bfs() // Ignore the already visited nodes
        else {
          visited(n.p) = true // Mark node as visited
          val choiceBegin = n.p + 1
          val choiceEnd = math.min(destination, n.p + 6)
          val nMove = n.move + 1

          (choiceBegin to choiceEnd).foreach { pos => {
            if (move(pos) == -1) q.enqueue(Node(pos, nMove))
            else q.enqueue(Node(move(pos), nMove))
          }}

          bfs()
        }
      }
    }

    // Driver
    populateMove(1, n - 1, 0, true)
    bfs()
  }

  def main(args: Array[String]): Unit = {
    println(snakesAndLadders(Array(
      Array(-1,-1,-1,-1,-1,-1),
      Array(-1,-1,-1,-1,-1,-1),
      Array(-1,-1,-1,-1,-1,-1),
      Array(-1,35,-1,-1,13,-1),
      Array(-1,-1,-1,-1,-1,-1),
      Array(-1,15,-1,-1,-1,-1)
    )))
  }
}