package in.kamranali.leetcode.medium

import scala.annotation.tailrec

object BasicCalculatorII227 {

    def calculate(s: String): Int = {

        val validOperators = Set('+', '-', '/', '*')

        @tailrec
        def eval(remaining: String, st: List[Int], prevOperation: Char, number: Int): Int = {

            if (remaining.isEmpty) {
                // handle the last operator
                prevOperation match {
                    case '+' => st.sum + number
                    case '-' => st.sum - number
                    case '*' =>
                        val newNumber = st.head * number
                        st.tail.sum + newNumber
                    case '/' =>
                        val newNumber = st.head / number
                        st.tail.sum + newNumber
                }
            }
            else if (remaining.head == " ".charAt(0)) {
                // handle the empty strings
                eval(remaining.tail, st, prevOperation, number)
            }
            else {
                val elem = remaining.head

                if (validOperators.contains(elem)) {
                    // if current character is an operator,
                    // at this point we have operands for previous operator so act on it
                    prevOperation match {
                        case '+' =>
                            eval(remaining.tail, number :: st, elem, 0)
                        case '-' =>
                            eval(remaining.tail, -number :: st, elem, 0)
                        case '*' =>
                            val newNumber = st.head * number
                            eval(remaining.tail, newNumber :: st.tail, elem, 0)
                        case '/' =>
                            val newNumber = st.head / number
                            eval(remaining.tail, newNumber :: st.tail, elem, 0)
                    }
                } else {
                    // if current character is number keep on building the operand
                    val newNumber = number * 10 + elem.toString.toInt
                    eval(remaining.tail, st, prevOperation, newNumber)
                }
            }

        }

        eval(s, List(), '+', 0)

    }

    def main(args: Array[String]): Unit = {
        println(calculate("4/3+2"))
    }
}