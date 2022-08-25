package guide.atech.algorithms.cflt.functionlibrary

import guide.atech.algorithms.cflt.functionlibrary.ds.Trie
import guide.atech.algorithms.cflt.functionlibrary.model.{FunctionLibrary, MyFunction}

class BasicFunctionLibrary extends FunctionLibrary[MyFunction, String] {

  private val trie = new Trie("/")

  override def register(functionSet: Set[MyFunction]): Unit = functionSet.foreach(function => trie.insert(function))
  override def findMatches(argumentTypes: List[String]): List[String] = trie.findMatches(argumentTypes.toArray)
}
