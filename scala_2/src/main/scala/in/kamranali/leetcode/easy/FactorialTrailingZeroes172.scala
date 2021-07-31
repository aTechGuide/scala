package in.kamranali.leetcode.easy

object FactorialTrailingZeroes172 {
    def trailingZeroes(n: Int): Int = {
        
        def countFTwo(remaining: Int, res: Int): Int = {
            if (remaining < 2) res
            else {
                if (remaining % 2 == 0) countFTwo(remaining / 2, res + 1)
                else res
            }
        }
        
        def countFFive(remaining: Int, res: Int): Int = {
            if (remaining < 5) res
            else {
                if (remaining % 5 == 0) countFFive(remaining / 5, res + 1)
                else res
            }
        }
        
        def factorial(remaining: Int, cTwo: Int, cFive: Int): Int = {
            if (remaining  <= 1) math.min(cTwo, cFive)
            else {
                val ncTwo = cTwo + countFTwo(remaining, 0)
                val ncFive = cFive + countFFive(remaining, 0)
                
                factorial(remaining - 1, ncTwo, ncFive)
            }
            
        }
        
        factorial(n, 0, 0)
        
    }

//  def main(args: Array[String]): Unit = {
//    println(trailingZeroes(5))
//  }

  def main(args: Array[String]): Unit = {
    println("abc".substring(0, 1))

    Array(Array(0)).clone()
  }
}