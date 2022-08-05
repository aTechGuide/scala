package guide.atech.algorithms.rl

import guide.atech.algorithms.rl.data.{CustomerLimits, CustomerLimitsWithCredits}

trait CustomerDetailsWithCredits {
  def memoryWithCredits: Map[Int, CustomerLimitsWithCredits]
}
