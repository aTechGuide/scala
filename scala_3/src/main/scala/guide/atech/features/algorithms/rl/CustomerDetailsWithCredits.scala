package guide.atech.features.algorithms.rl

import guide.atech.features.algorithms.rl.data.{CustomerLimits, CustomerLimitsWithCredits}

trait CustomerDetailsWithCredits {
  def memoryWithCredits: Map[Int, CustomerLimitsWithCredits]
}
