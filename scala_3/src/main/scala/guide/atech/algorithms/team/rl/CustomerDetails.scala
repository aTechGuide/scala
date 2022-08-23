package guide.atech.algorithms.team.rl

import guide.atech.algorithms.team.rl.data.{CustomerLimits, CustomerLimitsWithCredits}

trait CustomerDetails {
  def memory: Map[Int, CustomerLimits]
}
