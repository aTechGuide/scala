package guide.atech.features.algorithms.rl

import guide.atech.features.algorithms.rl.data.CustomerLimits
import guide.atech.features.algorithms.rl.data.CustomerLimitsWithCredits

trait CustomerDetails {
  def memory: Map[Int, CustomerLimits]
}
