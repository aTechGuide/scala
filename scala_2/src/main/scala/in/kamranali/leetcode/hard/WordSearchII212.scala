package in.kamranali.leetcode.hard

object WordSearchII212 {
  class Trie(val char : Option[Char] = None) {
    import scala.annotation.tailrec
    import scala.collection.mutable

    var isWord = false
    val children = mutable.Map.empty[Char, Trie]

    /** Inserts a word into the trie.
    At each step we are at parent Node, trying to search for a child, and inserting it if NOT found
      */
    def insert(key: String): Unit = {

      @tailrec
      def appendHelper(currentIndex: Int, node: Trie): Unit = {
        if (currentIndex == key.length) {
          node.isWord = true // Marking the last inserted Character as a Word
        } else {
          val char = key.charAt(currentIndex)
          val result = node.children.getOrElseUpdate(char, new Trie(Some(char)))

          appendHelper(currentIndex + 1, result)
        }
      }

      appendHelper(0,this)
    }
  }

  def findWords(board: Array[Array[Char]], words: Array[String]): List[String] = {

    // Initializations
    val trie = new Trie()
    words.foreach { word => trie.insert(word)}

    val rows = board.length
    val cols = board(0).length

    val visited = Array.fill[Boolean](rows, cols)(false)
    var res = Set.empty[String]
    val directions = Array(1->0, -1->0, 0->1, 0-> -1)

    // Auxiliary Function
    def isValidDir(x: Int, y: Int): Boolean = {x >= 0 && x < rows && y >=0 && y < cols}

    def searchWord(node: Trie, r: Int, c: Int, pre: String): Unit = {
      val elem = board(r)(c)
      visited(r)(c) = true

      // Matching will begin from children
      node.children.foreach { tuple => {
        if (elem == tuple._1) {
          if (tuple._2.isWord) {
            // word found
            val nPre = pre + elem.toString
            res = res + nPre
          }

          directions
            .map(p => (p._1 + r) -> (p._2 + c))
            .filter(p => isValidDir(p._1, p._2) && !visited(p._1)(p._2))
            .foreach { p => {
              val nPre = pre + elem.toString
              searchWord(tuple._2, p._1, p._2, nPre)
            }}
        }
      }}

      visited(r)(c) = false
    }

    // Driver
    (0 until rows).foreach { row => {
      (0 until cols).foreach { col => {
        searchWord(trie, row, col, "")
      }}
    }}

    res.toList
  }

  def main(args: Array[String]): Unit = {
    println(findWords(Array(Array('e', 'a')), Array("ea")))
  }
}