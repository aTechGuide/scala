package in.kamranali.leetcode.easy

object PowerofThree326 {
    def isPowerOfThree(n: Int): Boolean = {
        
        def calc(factor: Long): Boolean = {

            println("checking for factor " + factor)
            if (n == factor) true
            else if (n > factor) calc(factor * 3)
            else false
        }
        
        if (n == 0) false
        else calc(3)
        
    }

    def main(args: Array[String]): Unit = {
        println(isPowerOfThree(2147483647))
    }
}