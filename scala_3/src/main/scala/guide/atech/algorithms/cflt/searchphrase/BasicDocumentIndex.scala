package guide.atech.algorithms.cflt.searchphrase

case class Pair(docId: Int, position: Int)
class BasicDocumentIndex extends DocumentIndex {

  import scala.collection.mutable.Map
  import scala.collection.mutable.Set

  private val index = Map.empty[String, Set[Pair]]

  override def addDocument(id: Int, text: String): Unit = {
    val sanitized = text.replaceAll("[.,!]", "")

    val wordArr = sanitized.split(" ")

    wordArr.indices.foreach { idx => {
      val k = wordArr(idx).toLowerCase
      val value = index.getOrElseUpdate(k, Set.empty[Pair])
      value.add(Pair(id, idx))
    }}
  }

  override def search(pattern: String): List[Int] = {
    val pattArr = pattern.split(" ").map(_.toLowerCase)
    val pattLen = pattArr.length

    def util(idx: Int, candidates: List[Pair]): List[Int] = {
      if (idx == pattLen) candidates.map(_.docId).distinct
      else {
        val curWordToMatch = pattArr(idx)
        val curWordPairFromIndexSet = index.getOrElse(curWordToMatch, Set.empty[Pair])

//        println(candidates)
//        println(curWordPairFromIndexSet)
        val newCandidates = candidates
          .map( candidate => Pair(candidate.docId, candidate.position + 1))
          .filter { candidate => {
            curWordPairFromIndexSet.contains(Pair(candidate.docId, candidate.position))
        }}

        util(idx + 1, newCandidates)
      }
    }

    util(1, index.getOrElse(pattArr(0), Set.empty[Pair]).toList)
  }
}
