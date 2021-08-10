package in.kamranali.leetcode.medium

object GroupAnagrams49 {
    def groupAnagrams(strs: Array[String]): List[List[String]] = {
        
        def createKey(k: String): String = {
            val arr = Array.fill[Int](26)(0)
            
            k.foreach{ c =>
                val idx = c - 'a'
                arr(idx) = arr(idx) + 1
            }
            
            arr.mkString(",")
        }
        
        def process(idx: Int, mem: Map[String, List[String]], res: List[List[String]]): List[List[String]] = {
            
            if (idx == strs.length)
                mem.values.toList
            else {
                val elem = strs(idx)
                val key = createKey(elem)
                
                val value = mem.getOrElse(key, List())
                val newMem = mem + (key -> (elem :: value))
                
                process(idx + 1, newMem, res)
            }
            
        }
        process(0, Map(), List())
    }

  def main(args: Array[String]): Unit = {

    println(groupAnagrams(Array("bdddddddddd","bbbbbbbbbbc")))

    Array(1,2,3).clone()

  }
}