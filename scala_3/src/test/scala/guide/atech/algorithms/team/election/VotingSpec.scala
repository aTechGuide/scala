package guide.atech.algorithms.team.election

import guide.atech.algorithms.team.election.Voting

class VotingSpec extends munit.FunSuite {

  test("Winner is B") {
    val data = Array("A", "C", "E", "B", "A", "B", "C", "B", "D", "B", "A")
    assertEquals(Voting.findWinner(data), "B")
  }

  test("Winner is A") {
    val data = Array("A", "A", "B", "B")
    assertEquals(Voting.findWinner(data), "A")
  }
}
