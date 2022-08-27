package guide.atech.algorithms.cflt.ide_autocomplete.ds

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

/*
  Ref
    - https://leetcode.com/playground/MHs8BSFc
*/
class Trie(c: Option[Char] = None) {
  import scala.collection.mutable

  private var isWord = false
  private val words = ListBuffer.empty[String]
  private val children = mutable.Map.empty[Char, Trie]

  def insert(key: String): Unit = {

    @tailrec
    def appendHelper(currentIndex: Int, parentNode: Trie): Unit = {
      if (currentIndex == key.length) {
        parentNode.isWord = true // Marking the last inserted Character as a Word
        parentNode.words.addOne(key) // Storing the word at this place
      }
      else {
        val char = key.charAt(currentIndex)
        val result = parentNode.children.getOrElseUpdate(char, new Trie(Some(char)))

        appendHelper(currentIndex + 1, result)
      }
    }

    appendHelper(0, this)
  }

  def startsWith(prefix: String): List[String] = {

    def findWords(node: Trie): List[String] = {
      if (node.isWord)
        node.words.toList ++ {
          node.children.foldLeft(List.empty[String])((acc, tup) => {
            acc ++ {
              val (c, trie) = tup
              findWords(trie)
            }
          })
        }
      else {
        node.children.foldLeft(List.empty[String])((acc, tup) => {
          acc ++ {
            val (c, trie) = tup
            findWords(trie)
          }
        })
      }
    }

    def helper(currentIndex: Int, node: Trie): List[String] = {
      if (currentIndex == prefix.length) findWords(node)
      else {
        val curChar = prefix.charAt(currentIndex)

        if (currentIndex == 0 || curChar.isLower || node.children.contains(curChar)) {
          node.children.get(prefix.charAt(currentIndex)) match {
            case Some(child) => helper(currentIndex + 1, child)
            case None => List()
          }
        } else {
          node.children.foldLeft(List.empty[String])((acc, child) => {
            acc ++ {
              helper(currentIndex, child._2)
            }
          })
        }

      }
    }

    helper(0, this)
  }

}
