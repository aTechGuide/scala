package guide.atech.features.algorithms.rl.data

import guide.atech.features.algorithms.rl.data.CustomerLimits

class CustomerLimitsWithCredits(id: Long, refillRate: Int, lastRefillTimeStamp: Long, availableTokens: Int, maxCapacity: Int, var credit: Int, val maxCredit: Int)
  extends CustomerLimits(id, refillRate, lastRefillTimeStamp, availableTokens, maxCapacity)