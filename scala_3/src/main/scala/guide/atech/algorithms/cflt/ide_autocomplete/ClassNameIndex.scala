package guide.atech.algorithms.cflt.ide_autocomplete

class ClassNameIndex extends NameIndex {
  import guide.atech.algorithms.cflt.ide_autocomplete.ds.Trie

  private val trie = Trie()

  override def add(text: String): Unit = trie.insert(text)

  override def matchPrefix(prefix: String): List[String] = trie.startsWith(prefix)
}
