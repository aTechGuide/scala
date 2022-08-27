package guide.atech.algorithms.cflt.ide_autocomplete

trait NameIndex {
  def add(text: String): Unit
  def matchPrefix(prefix: String): List[String]
}
