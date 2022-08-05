package guide.atech.algorithms.rl

import guide.atech.algorithms.rl.data.{CustomerLimits, CustomerLimitsWithCredits}

trait CustomerDetails {
  def memory: Map[Int, CustomerLimits]
}
