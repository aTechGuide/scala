package guide.atech.algorithms.rl.data

class CustomerLimits(id: Long, val refillRate: Int, var lastRefillTimeStamp: Long, var availableTokens: Int, val maxCapacity: Int)