package guide.atech.algorithms.team.router.model

import scala.annotation.tailrec
import scala.collection.mutable

class Trie(word: String) {

  private var endMarker = false
  private val children = mutable.Map.empty[String, Trie]
  private var value = ""

  def insert(path: String, result: String): Unit = {

    val pathList = path.split("/").toList

    @tailrec
    def insertRec(parent: Trie, p: List[String]): Unit = {
      if (p.isEmpty) {
        parent.endMarker = true
        parent.value = result
      } else {
        val cur = p.head

        val child = parent.children.getOrElseUpdate(cur, new Trie(cur))
        insertRec(child, p.tail)
      }
    }

    insertRec(this, pathList)
  }

  def search(path: String): String = {
    val pathList = path.split("/").toList

    @tailrec
    def searchRec(parent: Trie, curPath: List[String]): String = {
      if (curPath.isEmpty) {
        if (parent.endMarker) parent.value
        else ""
      }
      else {
        val cur = curPath.head

        parent.children.get(cur) match
          case Some(child) => searchRec(child, curPath.tail)
          case None => ""
      }
    }

    searchRec(this, pathList)
  }

  def searchWithWildcard(path: String): String = {
    val pathList = path.split("/").toList

    def searchRec(parent: Trie, curPath: List[String]): String = {
      if (curPath.isEmpty) parent.value
      else {
        val cur = curPath.head

        if(cur == "*") {
          parent.children.foldLeft("")((acc, tup) => {
            if(acc.nonEmpty) acc // found answer already
            else {
              val (k, child) = tup
              searchRec(child, curPath.tail)
            }
          })
        } else {
          parent.children.get(cur) match
            case Some(child) => searchRec(child, curPath.tail)
            case None => ""
        }

      }
    }

    searchRec(this, pathList)
  }

}
