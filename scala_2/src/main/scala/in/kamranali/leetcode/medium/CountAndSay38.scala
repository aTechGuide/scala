package in.kamranali.leetcode.medium

object CountAndSay38 {
    def countAndSay(n: Int): String = {
        
        def pronounce(word: String): String = {
            
            def traverse(remaining: String, prev: Char, count: Int, res: String): String = {
                
                if (remaining.isEmpty) res + (count.toString + prev.toString)
                else {
                    val curr = remaining.head
                    
                    if (curr == prev) traverse(remaining.tail, prev, count + 1, res)
                    else {
                        val newRes = res + (count.toString + prev.toString)
                        traverse(remaining.tail, curr, 1, newRes)
                    }
                }
                
            }
            
            traverse(word.tail, word.head, 1, "")
        }
        
        def say(idx: Int, prev: String): String = {
            
            if (n == idx) prev
            else {
                val pronounciation = pronounce(prev)
                say(idx + 1, pronounciation)
            }
            
        }
        
        say(1, "1")
    }
}