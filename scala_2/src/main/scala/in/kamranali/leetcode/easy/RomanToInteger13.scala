package in.kamranali.leetcode.easy

object RomanToInteger13 {
    def romanToInt(s: String): Int = {
        
        val memory = Map('I' -> 1, 'V' -> 5, 'X' -> 10, 'L' -> 50, 'C' -> 100, 'D' -> 500, 'M' -> 1000)
        
        def count(rem: String, res: Int = 0): Int = {
            
            if (rem.isEmpty) res
            else {
                
                val h = rem.head
                val newRem = rem.tail
                
                val (newRes, fRem) =
                  if (h == 'I') {
                    if(newRem.isEmpty) (res + 1, newRem)
                    else {
                        val second = newRem.head
                        
                        if (second != 'I') {
                            (res + (memory(second) - 1), newRem.tail)
                        } else {
                            (res + memory(h), newRem)
                        }
                    }
                } else {
                    (res + memory(h), newRem)
                }
                
                count(fRem, newRes)
            }
        }
        
        count(s)
        
    }
}