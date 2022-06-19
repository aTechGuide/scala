package in.kamranali.leetcode.medium

class WordDictionary() {
  import in.kamranali.algorithms.trie.Trie
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