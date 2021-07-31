package in.kamranali.leetcode.easy

import scala.annotation.tailrec

object ValidAnagram242 {

    def isAnagramBetter(s: String, t: String): Boolean = {

        val init = Array.fill[Int](26)(0)

        val memory = s.foldLeft[Array[Int]](init) {
            case (m, value) =>
                val idx = value - 'a'
                m(idx) = m(idx) + 1
                m

        }

        @tailrec
        def verify(itr: Int): Boolean = {
            if (itr == t.length) {
                if (memory.sum == 0) true
                else false
            }
            else {
                val c: Char = t.charAt(itr)
                val idx = c - 'a'
                val count = memory(idx)

                if (count == 0) false
                else {
                    memory(idx) = count - 1
                    verify(itr + 1)
                }

            }
        }

        verify(0)

    }

    def isAnagram(s: String, t: String): Boolean = {
        
        val memory = s.foldLeft[Map[Char, Int]](Map()) {
            case (m, value) => 
                val count = m.getOrElse(value, 0)
                m + (value -> (count + 1))
        }
        
        @tailrec
        def verify(idx: Int, remaining: Map[Char, Int]): Boolean = {
            if (idx == t.length) {
                if (remaining.isEmpty) true
                else false
            }
            else {
                val c = t.charAt(idx)
                
                if (remaining.contains(c)) {
                    val count = remaining(c)
                    val newMap = if (count == 1) remaining - c else remaining + (c -> (count - 1))
                    
                    verify(idx + 1, newMap)
                }
                else false
            }
        }
        
        verify(0, memory)
        
    }

    def main(args: Array[String]): Unit = {

        println(isAnagram("anagram", "nagaram"))
    }
}