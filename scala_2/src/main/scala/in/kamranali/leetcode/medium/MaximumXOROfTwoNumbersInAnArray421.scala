package in.kamranali.leetcode.medium

object MaximumXOROfTwoNumbersInAnArray421 {
  /*
    For i = 6 prefixMask = 0 / 0 maxResult 0 / 0
    nPrefixMask = 1000000
    For num = 3 / 11 nPrefixMask & num = 0 / 0
    For num = 10 / 1010 nPrefixMask & num = 0 / 0
    For num = 5 / 101 nPrefixMask & num = 0 / 0
    For num = 25 / 11001 nPrefixMask & num = 0 / 0
    For num = 2 / 10 nPrefixMask & num = 0 / 0
    For num = 8 / 1000 nPrefixMask & num = 0 / 0
    leftPartOfNumSet = Set(0) Set(0)
    greedyTry = 64 / 1000000
    leftPartOfNum = 0 greedyTry = 64  b = 64
    isGreedyTryPossible = No

    For i = 5 prefixMask = 64 / 1000000 maxResult 0 / 0
    nPrefixMask = 1100000
    For num = 3 / 11 nPrefixMask & num = 0 / 0
    For num = 10 / 1010 nPrefixMask & num = 0 / 0
    For num = 5 / 101 nPrefixMask & num = 0 / 0
    For num = 25 / 11001 nPrefixMask & num = 0 / 0
    For num = 2 / 10 nPrefixMask & num = 0 / 0
    For num = 8 / 1000 nPrefixMask & num = 0 / 0
    leftPartOfNumSet = Set(0) Set(0)
    greedyTry = 32 / 100000
    leftPartOfNum = 0 greedyTry = 32  b = 32
    isGreedyTryPossible = No

    For i = 4 prefixMask = 96 / 1100000 maxResult 0 / 0
    nPrefixMask = 1110000
    For num = 3 / 11 nPrefixMask & num = 0 / 0
    For num = 10 / 1010 nPrefixMask & num = 0 / 0
    For num = 5 / 101 nPrefixMask & num = 0 / 0
    For num = 25 / 11001 nPrefixMask & num = 16 / 10000
    For num = 2 / 10 nPrefixMask & num = 0 / 0
    For num = 8 / 1000 nPrefixMask & num = 0 / 0
    leftPartOfNumSet = Set(0, 16) Set(0, 10000)
    greedyTry = 16 / 10000
    leftPartOfNum = 0 greedyTry = 16  b = 16
    isGreedyTryPossible = Yes

    For i = 3 prefixMask = 112 / 1110000 maxResult 16 / 10000
    nPrefixMask = 1111000
    For num = 3 / 11 nPrefixMask & num = 0 / 0
    For num = 10 / 1010 nPrefixMask & num = 8 / 1000
    For num = 5 / 101 nPrefixMask & num = 0 / 0
    For num = 25 / 11001 nPrefixMask & num = 24 / 11000
    For num = 2 / 10 nPrefixMask & num = 0 / 0
    For num = 8 / 1000 nPrefixMask & num = 8 / 1000
    leftPartOfNumSet = Set(0, 8, 24) Set(0, 1000, 11000)
    greedyTry = 24 / 11000
    leftPartOfNum = 0 greedyTry = 24  b = 24
    isGreedyTryPossible = Yes

    For i = 2 prefixMask = 120 / 1111000 maxResult 24 / 11000
    nPrefixMask = 1111100
    For num = 3 / 11 nPrefixMask & num = 0 / 0
    For num = 10 / 1010 nPrefixMask & num = 8 / 1000
    For num = 5 / 101 nPrefixMask & num = 4 / 100
    For num = 25 / 11001 nPrefixMask & num = 24 / 11000
    For num = 2 / 10 nPrefixMask & num = 0 / 0
    For num = 8 / 1000 nPrefixMask & num = 8 / 1000
    leftPartOfNumSet = Set(0, 8, 4, 24) Set(0, 1000, 100, 11000)
    greedyTry = 28 / 11100
    leftPartOfNum = 0 greedyTry = 28  b = 28
    leftPartOfNum = 8 greedyTry = 28  b = 20
    leftPartOfNum = 4 greedyTry = 28  b = 24
    isGreedyTryPossible = Yes

    For i = 1 prefixMask = 124 / 1111100 maxResult 28 / 11100
    nPrefixMask = 1111110
    For num = 3 / 11 nPrefixMask & num = 2 / 10
    For num = 10 / 1010 nPrefixMask & num = 10 / 1010
    For num = 5 / 101 nPrefixMask & num = 4 / 100
    For num = 25 / 11001 nPrefixMask & num = 24 / 11000
    For num = 2 / 10 nPrefixMask & num = 2 / 10
    For num = 8 / 1000 nPrefixMask & num = 8 / 1000
    leftPartOfNumSet = HashSet(10, 24, 2, 8, 4) HashSet(100, 10, 1010, 1000, 11000)
    greedyTry = 30 / 11110
    leftPartOfNum = 10 greedyTry = 30  b = 20
    leftPartOfNum = 24 greedyTry = 30  b = 6
    leftPartOfNum = 2 greedyTry = 30  b = 28
    leftPartOfNum = 8 greedyTry = 30  b = 22
    leftPartOfNum = 4 greedyTry = 30  b = 26
    isGreedyTryPossible = No

    For i = 0 prefixMask = 126 / 1111110 maxResult 28 / 11100
    nPrefixMask = 1111111
    For num = 3 / 11 nPrefixMask & num = 3 / 11
    For num = 10 / 1010 nPrefixMask & num = 10 / 1010
    For num = 5 / 101 nPrefixMask & num = 5 / 101
    For num = 25 / 11001 nPrefixMask & num = 25 / 11001
    For num = 2 / 10 nPrefixMask & num = 2 / 10
    For num = 8 / 1000 nPrefixMask & num = 8 / 1000
    leftPartOfNumSet = HashSet(5, 10, 25, 2, 3, 8) HashSet(11, 10, 1010, 11001, 101, 1000)
    greedyTry = 29 / 11101
    leftPartOfNum = 5 greedyTry = 29  b = 24
    leftPartOfNum = 10 greedyTry = 29  b = 23
    leftPartOfNum = 25 greedyTry = 29  b = 4
    leftPartOfNum = 2 greedyTry = 29  b = 31
    leftPartOfNum = 3 greedyTry = 29  b = 30
    leftPartOfNum = 8 greedyTry = 29  b = 21
    isGreedyTryPossible = No

    28
  */
  def findMaximumXOR(nums: Array[Int]): Int = {
    
    def find(i: Int, prefixMask: Int, maxResult: Int): Int = {
      if (i < 0) maxResult
      else {
        println(s"For i = $i prefixMask = $prefixMask / ${prefixMask.toBinaryString} maxResult $maxResult / ${maxResult.toBinaryString}")
        val bitTobeSet = 1 << i
        val nPrefixMask = prefixMask | bitTobeSet

        println(s"nPrefixMask = ${nPrefixMask.toBinaryString}")
        
        val leftPartOfNumSet = nums.foldLeft(Set.empty[Int])((accum, num) => {
          accum + {
            val maskedNum = nPrefixMask & num
            println(s"For num = $num / ${num.toBinaryString} nPrefixMask & num = $maskedNum / ${maskedNum.toBinaryString}")
            maskedNum
          }
        })

        println(s"leftPartOfNumSet = $leftPartOfNumSet ${leftPartOfNumSet.map(e => e.toBinaryString)}")
        
        val greedyTry = maxResult | bitTobeSet

        println(s"greedyTry = ${greedyTry} / ${greedyTry.toBinaryString}")
        
        val isGreedyTryPossible = leftPartOfNumSet.foldLeft(false)((accum, leftPartOfNum) => {
          accum || {
            val b = leftPartOfNum ^ greedyTry
            println(s"leftPartOfNum = $leftPartOfNum greedyTry = $greedyTry  b = $b")
            leftPartOfNumSet.contains(b)
          }
        })
        
        if (isGreedyTryPossible) {
          println(s"isGreedyTryPossible = Yes")
          println()
          find(i - 1, nPrefixMask, greedyTry)
        }
        else {
          println(s"isGreedyTryPossible = No")
          println()
          find(i - 1, nPrefixMask, maxResult)
        }
      }
    }
    
    find(6, 0, 0)
  }

  def main(args: Array[String]): Unit = {
    println(findMaximumXOR(Array(3,10,5,25,2,8)))
  }
}