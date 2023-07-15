package guide.atech.algorithms.team.rl.data

case class CustomerLimitsWithCredits(id: Long, rate: Int, var time: Long, var quota: Int, var credit: Int, maxCredit: Int)