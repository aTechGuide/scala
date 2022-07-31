package in.kamranali.algorithms.trie

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
    def appendHelper(currentIndex: Int, parentNode: Trie): Unit = {
      if (currentIndex == key.length) parentNode.isWord = true // Marking the last inserted Character as a Word
      else {
        val char = key.charAt(currentIndex)
        val result = parentNode.children.getOrElseUpdate(char, new Trie(Some(char)))

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
  def isValidPrefix(prefix: String): Boolean = {
    @tailrec
    def helper(currentIndex: Int, node: Trie): Boolean = {
      if (currentIndex == prefix.length) true
      else {
        node.children.get(prefix.charAt(currentIndex)) match {
          case Some(child) => helper(currentIndex + 1, child)
          case None => false
        }
      }
    }

    helper(0, this)
  }

  def startsWith(prefix: String): List[String] = {

    def findWords(state: String, node: Trie): List[String] = {
      if (node.isWord)
        List(state) ++ {
          node.children.foldLeft(List.empty[String])((acc, tup) => {
            acc ++ {
              val (c, trie) = tup
              findWords(state + c.toString, trie)
            }
          })
        }
      else {
        node.children.foldLeft(List.empty[String])((acc, tup) => {
          acc ++ {
            val (c, trie) = tup
            findWords(state + c.toString, trie)
          }
        })
      }
    }

    @tailrec
    def helper(currentIndex: Int, node: Trie): List[String] = {
      if (currentIndex == prefix.length) findWords(prefix, node)
      else {
        node.children.get(prefix.charAt(currentIndex)) match {
          case Some(child) => helper(currentIndex + 1, child)
          case None => List()
        }
      }
    }

    helper(0, this)
  }
}

object TrieTest extends App {

  val words = List("mobile","mouse","moneypot","monitor","mousepad")

  val trie = new Trie()
  words.foreach {trie.insert}

  println(trie.startsWith("m").sorted.take(3))
}