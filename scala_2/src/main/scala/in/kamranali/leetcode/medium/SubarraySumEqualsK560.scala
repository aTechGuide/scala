package in.kamranali.leetcode.medium

// https://www.youtube.com/watch?v=HbbYPQc-Oo4
object SubarraySumEqualsK560 {
    def subarraySum(nums: Array[Int], k: Int): Int = {
        def process(idx: Int, sum: Int, sumMap: Map[Int, Int], count: Int): Int = {
            if (idx == nums.length) count
            else {
                val cur = nums(idx)
                val newSum = sum + cur

                val newCounter = if (newSum == k) count + 1 else count
                val newCounter2 =
                    if (sumMap.contains(newSum - k)) newCounter + sumMap(newSum - k)
                    else newCounter

                val newSumMap = sumMap + (newSum -> (sumMap.getOrElse(newSum, 0) + 1))

                process(idx + 1, newSum, newSumMap, newCounter2)
            }
        }
        process(0, 0, Map(), 0)
    }

    def subarraySum2(nums: Array[Int], k: Int): Int = {

        val len = nums.length
        val memory = Array.fill[Int](len, len)(Int.MinValue)

        var counter = 0
        // fill single digit
        (0 until len).foreach {idx => {
            if (nums(idx) == k) counter = counter + 1
            memory(idx)(idx) = nums(idx)
        }}

        // fill rest
        (2 to len).foreach {interval => {
            val end = len - interval + 1
            (0 until end).foreach { i => {
                val j = i + interval - 1

                val ans = memory(i)(j - 1) + memory(j)(j)
                if (ans == k) counter = counter + 1
                memory(i)(j) = ans
            }}
        }}

        memory.foreach(row => println(row.mkString(" ")))

//        memory.foldLeft[Int](0)((accum, row) => {
//            val rowCount = row.count(_ == k)
//            accum + rowCount
//        })
        counter
    }

    def main(args: Array[String]): Unit = {
//        println(subarraySum(Array(3, 4, 7, 2, -3, 1, 4, 2), 7)) // 4
//
//        println(subarraySum(Array(0,0,0,0,0,0,0,0,0,0), 0)) // 55

//        println(subarraySum(Array(1,1,1), 2)) // 2

        println(subarraySum(Array(1,2,3), 3)) // 2

    }
}