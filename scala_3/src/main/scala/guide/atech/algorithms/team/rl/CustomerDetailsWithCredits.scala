package guide.atech.algorithms.team.rl

import guide.atech.algorithms.team.rl.data.{CustomerLimits, CustomerLimitsWithCredits}

trait CustomerDetailsWithCredits {
  def memoryWithCredits: Map[Int, CustomerLimitsWithCredits]
}
