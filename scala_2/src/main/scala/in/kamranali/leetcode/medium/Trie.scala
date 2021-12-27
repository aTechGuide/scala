package in.kamranali.leetcode.medium

class WordDictionary() {

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

    /** Returns if the word is in the trie. */
    def search(word: String): Boolean = {

      @tailrec
      def helper(currentIndex: Int, node: Trie): Boolean = {
        if (currentIndex == word.length) node.isWord
        else {
          node.children.get(word.charAt(currentIndex)) match {
            case Some(child) => helper(currentIndex + 1, child)
            case None => false
          }
        }
      }

      helper(0, this)
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    def startsWith(prefix: String): Boolean = {
      @tailrec
      def helper(currentIndex: Int, node: Trie): Boolean = {
        if (currentIndex == prefix.length) {
          true
        } else {
          node.children.get(prefix.charAt(currentIndex)) match {
            case Some(child) => helper(currentIndex + 1, child)
            case None => false
          }
        }
      }

      helper(0, this)
    }
  }

  val trie = new Trie()
  
  def addWord(word: String): Unit = trie.insert(word)

  def search(word: String): Boolean = {
    
    def searchHelper(idx: Int, node: Trie): Boolean = {
      if (word.length == idx) node.isWord
      else {
        val char = word.charAt(idx)
        
        if (char != '.') {
          node.children.get(char) match {
            case Some(child) => searchHelper(idx + 1, child)
            case None => false
          }
        } else {
          // Wild Card Matching
          node.children.foldLeft(false)((accum, tuple) => {
            accum || searchHelper(idx + 1, tuple._2)
          })
        }
      }
    }

    searchHelper(0, trie)
  }
}