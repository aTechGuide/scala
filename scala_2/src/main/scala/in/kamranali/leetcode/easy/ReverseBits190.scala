package in.kamranali.leetcode.easy

object ReverseBits190 {
    // you need treat n as an unsigned value
    def reverseBits(x: Int): Int = {
        
        def reverse(num: Int, rev: Int): Int = {
            if (num == 0) rev
            else {
                val newRev = rev << 1
                
                val finRev = if ((num & 1) == 1) newRev ^ 1 else newRev
                
                reverse(num >> 1, finRev)
            }
        }
        
        reverse(x, 0)
        
    }

  def main(args: Array[String]): Unit = {
    println(reverseBits(15))
  }
}