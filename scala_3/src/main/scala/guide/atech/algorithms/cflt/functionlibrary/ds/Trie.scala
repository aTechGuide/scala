package guide.atech.algorithms.cflt.functionlibrary.ds

import guide.atech.algorithms.cflt.functionlibrary.model.MyFunction

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map

class Trie(val value: String) {
  private val children = Map.empty[String, Trie]
  private val variadicFunc = ListBuffer.empty[String]
  private val nonVariadicFunc = ListBuffer.empty[String]

  def insert(key: MyFunction): Unit = {

    val lenArgs = key.arguments.length

    @tailrec
    def appendHelper(currentIndex: Int, parentNode: Trie): Unit = {
      if (currentIndex == lenArgs)
        if (key.isVariadic) parentNode.variadicFunc.addOne(key.name)
        else parentNode.nonVariadicFunc.addOne(key.name)
      else {
        val arg = key.arguments(currentIndex)
        val result = parentNode.children.getOrElseUpdate(arg, new Trie(arg))

        appendHelper(currentIndex + 1, result)
      }
    }

    appendHelper(0, this)
  }

  def findMatches(argumentTypes: Array[String]): List[String] = {
    val ans = ListBuffer.empty[String]
    val argsLen = argumentTypes.length

    def satisfy(str: String, i: Int): Boolean =
      (i until argsLen).forall(idx => argumentTypes(idx) == str)

    @tailrec
    def helper(currentIndex: Int, node: Trie): Unit = {
      if (currentIndex == argsLen) {
        ans.addAll(node.variadicFunc)
        ans.addAll(node.nonVariadicFunc)
      }
      else {
        val arg = argumentTypes(currentIndex)
        //println(currentIndex)
        val children = node.children.get(arg)
        children match {
          case Some(child) =>
            if (child.variadicFunc.nonEmpty && (currentIndex + 1 <= argsLen - 1)) {

              if (satisfy(child.value, currentIndex + 1)) {
                ans.addAll(child.variadicFunc)
              }
            }
            helper(currentIndex + 1, child)
          case
            None =>
        }
      }
    }

    helper(0, this)
    ans.toList
  }
}
