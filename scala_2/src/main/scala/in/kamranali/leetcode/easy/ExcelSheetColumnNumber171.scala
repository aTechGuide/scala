package in.kamranali.leetcode.easy

object ExcelSheetColumnNumber171 {
  def titleToNumber(columnTitle: String): Int = {

    val length = columnTitle.length

    def parse(itr: Int, res: Int): Int = {

      if (itr < 0) res
      else {

        val char = columnTitle.charAt(itr)
        val charValue = char - 'A' + 1

        val dif = length - (itr + 1)
        val newRes = if (res == 0) charValue else math.pow(26, dif).toInt * charValue + res

        parse(itr - 1, newRes)
      }
    }

    parse(length - 1, 0)

  }

  def main(args: Array[String]): Unit = {
    println(titleToNumber("ZY"))
  }
}