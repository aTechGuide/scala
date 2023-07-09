package guide.atech.algorithms.team.router

import guide.atech.algorithms.team.router.model.Trie

class TrieRouter extends Router {
  
  val state = new Trie("")
  
  override def withRoute(path: String, result: String): Unit = this.synchronized {
    state.insert(path, result)
  }

  override def route(path: String): String = this.synchronized {
    //state.search(path)
    state.searchWithWildcard(path)
  }
}
