package guide.atech.algorithms.team.router

import munit.FunSuite

class RouterTest extends FunSuite {

  test("Basic Routing is working properly") {

    val path = "/abc"
    val result = "/def"

    val router = new BasicRouter()
    router.withRoute(path, result)

    assertEquals(router.route(path), result)
  }

  test("Trie Routing is working properly") {

    val path = "/abc/def/ijk/lmn"
    val result = "/xyz"

    val router = new TrieRouter()
    router.withRoute(path, result)

    assertEquals(router.route(path), result)
  }

  test("Trie Routing is working properly") {

    val path = "/abc/def/ijk/lmn"
    val result = "/xyz"

    val router = new TrieRouter()
    router.withRoute(path, result)

    assertEquals(router.route("wrong/path"), "")
  }

  test("Trie Routing is working properly with wildcard") {

    val path = "/abc/def/ijk/lmn"
    val searchPath = "/abc/*/ijk/lmn"
    val result = "/xyz"

    val router = new TrieRouter()
    router.withRoute(path, result)

    assertEquals(router.route(searchPath), result)
  }

}
