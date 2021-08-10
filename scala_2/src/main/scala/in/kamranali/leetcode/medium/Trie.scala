package in.kamranali.leetcode.medium

import scala.annotation.tailrec
import scala.collection.mutable

/*
    - First node doesn't contain a character, but acts as a starting node for insertion / search
    - https://leetcode.com/problems/implement-trie-prefix-tree/submissions/
    - https://mauricio.github.io/2015/01/06/building-a-prefix-tree-in-scala.html
 */
class Trie(val char : Option[Char] = None) {

    private var isWord = false
    private val children = mutable.Map.empty[Char, Trie]

    /** Inserts a word into the trie. */
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
            if (currentIndex == word.length) {
                node.isWord
            } else {
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

object TrieTest {
    def main(args: Array[String]): Unit = {
        val root = new Trie()

        root.insert("ab")
        println(root.startsWith("a"))
    }
}

