package guide.atech.algorithms.fs

class FileSystemSpec extends munit.FunSuite {

  import Collection._
  test("size of file is 300") {

    val fs = FileSystem()
    fs.addFile(MyFile("file1", 100, None))
    fs.addFile(MyFile("file2", 200, Some(Collection1)))
    fs.addFile(MyFile("file3", 200, Some(Collection1)))
    fs.addFile(MyFile("file4", 300, Some(Collection2)))
    fs.addFile(MyFile("file5", 100, None))

    assertEquals(fs.totalSize, 900)
  }

  test("top 2 collection is correct") {

    val fs = FileSystem()
    fs.addFile(MyFile("file1", 100, None))
    fs.addFile(MyFile("file2", 200, Some(Collection1)))
    fs.addFile(MyFile("file3", 200, Some(Collection1)))
    fs.addFile(MyFile("file4", 300, Some(Collection2)))
    fs.addFile(MyFile("file5", 100, None))

    assertEquals(fs.getTopKCollection(2).toList, List(Collection1, Collection2))
  }

}
