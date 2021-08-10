package in.kamranali.leetcode.medium

object LetterCombinationsOfAPhoneNumber17 {
    def letterCombinations(digits: String): List[String] = {
        
        val mapping = Map(2 -> "abc", 3 -> "def", 4 -> "ghi", 5 -> "jkl", 6 -> "mno", 7 -> "pqrs", 8 -> "tuv", 9 -> "wxyz")
        
        def createRes(mapped: String, res: List[String]): List[String] = {
            mapped.flatMap(elem => res.map(l => l + elem)).toList
        }
        
        def combinations(remaining: String, res: List[String]): List[String] = {
            
            if (remaining.isEmpty) res
            else {
                val curr = remaining.head - '0'
                val mapped = mapping(curr)
                
                val newRes = createRes(mapped, res)
              combinations(remaining.tail, newRes)
            }
            
        }

      combinations(digits, List(""))
    }

  def main(args: Array[String]): Unit = {
    println(letterCombinations("23"))
  }
}