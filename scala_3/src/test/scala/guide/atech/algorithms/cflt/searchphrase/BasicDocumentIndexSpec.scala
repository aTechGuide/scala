package guide.atech.algorithms.cflt.searchphrase

class BasicDocumentIndexSpec extends munit.FunSuite {

  test("We are searching correctly") {
    val docIdx = BasicDocumentIndex()

    docIdx.addDocument(1, "Cloud computing is the on-demand availability of computer system resources.")
    docIdx.addDocument(2, "One integrated service for metrics uptime cloud monitoring dashboards and alerts reduces time spent navigating between systems.")
    docIdx.addDocument(3,  "Monitor entire cloud infrastructure, whether in the cloud computing is or in virtualized data centers.")

    assertEquals(docIdx.search("cloud"), List(3, 2, 1))
    assertEquals(docIdx.search("cloud monitoring"), List(2))
    assertEquals(docIdx.search("Cloud computing is"), List(3, 1))
  }

}
