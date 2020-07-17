package in.kamranali.algorithms.dp


/**
  * Reference
  *
  * - https://www.youtube.com/watch?v=xCbYmUPvc2Q&t=334s
  */

class P_3_01Knapsack {

  def solve(items: Map[Int, Int], weight: Int): Int  = {

    val rows = items.size
    val cols = weight + 1
    val memory = Array.ofDim[Int](rows, cols)
    val itemList = items.keySet.toList

    (0 until rows).foreach { row =>
      val currentItemWeight = itemList(row)
      val currentValue = items(currentItemWeight)

      (1 until cols).foreach { col =>

        memory(row)(col) =
          if (row == 0)
            if (currentItemWeight > col) 0
            else currentValue
          else {
            val valueAbove = memory(row - 1)(col)
            if (col - currentItemWeight >= 0) math.max(valueAbove, currentValue + memory(row - 1)(col - currentItemWeight))
            else valueAbove
          }

      }
    }

    // memory.foreach {row => println(row.mkString(" "))}
    memory(rows - 1)(cols - 1)

  }

}

object P_3_01KnapsackTest extends App {
  val sol = new P_3_01Knapsack()

  // Test 1
  var A = Map[Int, Int](5 -> 60, 3 -> 50, 4 -> 70, 2 -> 30)
  var w = 5
  var data = sol.solve(A, w)
  assert(data == 80)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.solve(A)
//  assert(data == 42)

}

