package in.kamranali.leetcode.hard

object MaximumProfitInJobScheduling1235 {

  case class Job(startTime: Int, endTime: Int, var profit: Int)

  // Ref == LM
  def jobScheduling(startTime: Array[Int], endTime: Array[Int], profit: Array[Int]): Int = {
    
    // Initializations
    val len = startTime.length
    val jobs = Array.fill[Job](len)(null)

    // Step 1: Create Job Array
    (0 until len).foreach { idx => {
      jobs(idx) = new Job(startTime(idx), endTime(idx), profit(idx))
    }}

    // Step 2: Sort Job Array based on Finish Time
    val ordering = Ordering.fromLessThan[Job]((a, b) => a.endTime < b.endTime)
    val sortedJobs = jobs.sorted(ordering)
    
    // Auxiliary Functions
    def findMaxProfit(i: Int, j: Int, maxProfitForI: Int, maxSoFar: Int): Int = {
      if (i == len) maxSoFar
      else if (j == i) {
        // update the profit for I
        sortedJobs(i).profit = maxProfitForI

        // Update maxSoFar
        val newMaxSoFar = math.max(maxProfitForI, maxSoFar)

        // Calculate profit for next Iteration
        val newProfit = if ((i + 1) == len) 0 else sortedJobs(i + 1).profit
        
        findMaxProfit(i + 1, 0, newProfit, newMaxSoFar)
      }
      else {
        // We are computing the Max Profit that I can have
        val newProfitForI = 
          if (sortedJobs(j).endTime <= sortedJobs(i).startTime) {
            // Non Overlapping
            math.max(maxProfitForI, sortedJobs(i).profit + sortedJobs(j).profit)
          } else maxProfitForI
        
        findMaxProfit(i, j +1 , newProfitForI, maxSoFar)
      }
    }
    
    // Step 3: Iterate over array
    findMaxProfit(1, 0, sortedJobs(1).profit, sortedJobs(0).profit)
  }

  def main(args: Array[String]): Unit = {
    println(jobScheduling(Array(11, 3), Array(16, 8), Array(19, 7)))
  }
}