package guide.atech.algorithms.team.least_frequently_used

import munit.FunSuite

class LFUCacheTest extends FunSuite {

  test("We should be able to insert and fetch data") {

    val cache = new LFUCache[Int, String](2)

    assert(cache.put(1, "one").isEmpty)
    assertEquals(cache.get(1), Some("one"))
  }

  test("We should be able to evict based on LFU policy") {

    val cache = new LFUCache[Int, String](2)

    assert(cache.put(1, "one").isEmpty)
    assert(cache.put(2, "two").isEmpty)
    assertEquals(cache.get(1), Some("one"))

    assert(cache.put(3, "three").isEmpty)
    assertEquals(cache.get(2), None)
  }

  test("We should be able to increase frequency while putting same key") {

    val cache = new LFUCache[Int, String](2)

    assert(cache.put(1, "one").isEmpty)
    assert(cache.put(2, "two").isEmpty)
    assert(cache.put(1, "one").contains("one"))

    assert(cache.put(3, "three").isEmpty)
    assertEquals(cache.get(2), None)
  }

}
