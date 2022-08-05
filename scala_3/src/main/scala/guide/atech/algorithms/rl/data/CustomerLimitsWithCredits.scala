package guide.atech.algorithms.rl.data

class CustomerLimitsWithCredits(id: Long, refillRate: Int, lastRefillTimeStamp: Long, availableTokens: Int, maxCapacity: Int, var credit: Int, val maxCredit: Int)
  extends CustomerLimits(id, refillRate, lastRefillTimeStamp, availableTokens, maxCapacity)