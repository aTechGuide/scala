package in.kamranali.leetcode.medium

object DivideTwoIntegers29 {
    def divide(dividend: Int, divisor: Int): Int = {

        val sign = if ((dividend < 0) ^ (divisor < 0)) -1 else 1

        def divideTwiceDivident(dividend: Long, divisor: Long, shifter: Int): Int = {
            val shifterDivisor = divisor << shifter

            if (dividend < shifterDivisor) shifter
            else divideTwiceDivident(dividend, divisor, shifter + 1)
        }

        def divideUtil(remaining: Long, divisor: Long, res: Int): Int = {

            if (remaining < divisor) res
            else {
                val shifter = divideTwiceDivident(remaining, divisor, 0)
                val newRes = 1 << (shifter - 1)
                val newRemaining = remaining - (divisor << shifter - 1)

                divideUtil(newRemaining, divisor, newRes + res)
            }
        }

        if (dividend == Int.MinValue && divisor == -1) Int.MaxValue
        else {
            val ans = divideUtil(math.abs(dividend.toLong), math.abs(divisor.toLong), 0)
            if (sign == 1) ans else -ans
        }
    }

    def main(args: Array[String]): Unit = {
        println(divide(1, 1)) // -1
    }
}