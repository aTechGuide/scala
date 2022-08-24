package guide.atech.algorithms.misc

import guide.atech.algorithms.team.misc.RandomNumberBetweenRange

class RandomNumberBetweenRangeSpec extends munit.FunSuite {

  test("We are generating random number in correct range") {

    val ans = RandomNumberBetweenRange.randomNumberMultiThreaded(100, 105)

    (0 to 100).foreach { _ => {
      assertEquals(ans < 105, true)
      assertEquals(ans >= 100, true)
    }}
  }

}
