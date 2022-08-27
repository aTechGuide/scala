package guide.atech.algorithms.cflt.searchphrase

trait DocumentIndex {
  def addDocument(id: Int, text: String): Unit
  def search(pattern: String): List[Int]
}
