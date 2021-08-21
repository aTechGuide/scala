package in.kamranali.fpcourse.various

import scala.annotation.tailrec

object Eval {

  def eval(expr: String): Int = {
    val operators = Set("+", "-", "*", "/")

    def getOperators: List[String] = expr.split(" ").filter(operators.contains).toList
    def getNumbers: List[Int] = expr.split(" ").filter(!operators.contains(_)).map(_.toInt).toList

    def simpleOperation(op1: Int, op2: Int, operator: String) = operator match {
      case "+" => op1 + op2
      case "-" => op1 - op2
      case "*" => op1 * op2
      case "/" => op1 / op2
      case _ => throw new IllegalArgumentException(s"Invalid Operator: $operator")
    }

    def priority(operator: String): Int = operator match {
      case "+" | "-" => 1
      case "*" | "/" => 2
      case _ => 0
    }

    /*
      1 + 2 * 3
      evalTailrec([1,2,3], [+, *], [], [])
      = evalTailrec([2,3], [+,*], [1], [])
      = evalTailrec([2,3], [*], [1], [+])
      = evalTailrec([3], [*], [2,1], [+])
      = evalTailrec([3], [], [2,1], [*,+])
      = evalTailrec([], [], [3,2,1], [*,+])
      = evalTailrec([], [], [6, 1], [+])
      = evalTailrec([], [], [7], [])
      = 7
      1 * 2 + 3
      evalTailrec([1,2,3], [*,+], [], [])
      = evalTailrec([2,3], [*,+], [1], [])
      = evalTailrec([2,3], [+], [1], [*])
      = evalTailrec([3], [+], [2,1], [*])
      = evalTailrec([3], [+], [2], [])
      = evalTailrec([3], [], [2], [+])
      = evalTailrec([], [], [3,2], [+])
      = evalTailrec([], [], [5], [])
      = 5
      1 - 2 - 3 * 4
      evalTailrec([1,2,3,4], [-,-,*], [], [])
      = evalTailrec([2,3,4], [-,-,*], [1], [])
      = evalTailrec([2,3,4], [-,*], [1], [-])
      = evalTailrec([3,4], [-,*], [2,1], [-])
      = evalTailrec([3,4], [-,*], [-1], [])
      = evalTailrec([3,4], [*], [-1], [-])
      = evalTailrec([4], [*], [3,-1], [-])
      = evalTailrec([4], [], [3,-1], [*,-])
      = evalTailrec([], [], [4,3,-1], [*,-])
      = evalTailrec([], [], [12, -1], [-])
      = evalTailrec([], [], [-13], [])
      = -13
     */
    @tailrec
    def evalTailrec(remainingOperands: List[Int], remainingOperators: List[String], operandStack: List[Int], operatorStack: List[String]): Int = {

      if (remainingOperands.isEmpty) {
        if (operatorStack.isEmpty) operandStack.head
        else {
          val op2 = operandStack.head
          val op1 = operandStack.tail.head
          val operator = operatorStack.head
          val result = simpleOperation(op1, op2, operator)

          evalTailrec(remainingOperands, remainingOperators, result :: operandStack.drop(2), operatorStack.tail)
        }
      } else if (remainingOperands.length > remainingOperators.length) {
        evalTailrec(remainingOperands.tail, remainingOperators, remainingOperands.head :: operandStack, operatorStack)
      } else if (operatorStack.isEmpty || priority(operatorStack.head) < priority(remainingOperators.head)) {
        evalTailrec(remainingOperands, remainingOperators.tail, operandStack, remainingOperators.head :: operatorStack)
      } else {
        val op2 = operandStack.head
        val op1 = operandStack.tail.head
        val operator = operatorStack.head
        val result = simpleOperation(op1, op2, operator)

        evalTailrec(remainingOperands, remainingOperators, result :: operandStack.drop(2), operatorStack.tail)
      }

    }

    evalTailrec(getNumbers, getOperators, List(), List())
  }

  def main(args: Array[String]): Unit = {
    println(eval("1 + 2")) // 3
    println(eval("1 + 2 + 3")) // 6
    println(eval("1 + 2 * 3")) // 7
    println(eval("1 * 2 + 3")) // 5
    println(eval("1 - 2")) // -1
    println(eval("1 - 2 * 3")) // -5
    println(eval("1 + 2 * 3 + 4 / 5 + 6 * 7 - 8")) // 41
  }

}
