package guide.atech.algorithms.team.rl.data

class CustomerLimits(id: Long, val refillRate: Int, var _lastRefillTimeStamp: Long, var availableTokens: Int, val maxCapacity: Int) {
  def lastRefillTimeStamp: Long = _lastRefillTimeStamp
  def lastRefillTimeStamp_=(time: Long): Unit = _lastRefillTimeStamp = time
}