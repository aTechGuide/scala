package in.kamranali.leetcode.medium

object Pow50 {
    def myPow(x: Double, n: Int): Double = {

        val shouldInvert = if (n > 0) false else true
        val unsignedN = math.abs(n.toLong)

        def util(idx: Long): Double = {
            if (idx == 1) x
            else {
                val subProb = util(idx / 2)
                if (idx % 2 == 0) subProb * subProb
                else x * subProb * subProb
            }
        }

        if (n == 0) 1
        else if (x == 1.00000) x
        else {
            val ans = util(unsignedN)
            // adjust sign
            if (shouldInvert) 1 / ans else ans
        }

    }

    def main(args: Array[String]): Unit = {
        println(myPow(2.00000, -2147483648))
    }
}