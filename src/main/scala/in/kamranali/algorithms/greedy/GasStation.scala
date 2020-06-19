package in.kamranali.algorithms.greedy

/**
  * https://www.interviewbit.com/problems/gas-station/
  */

class GasStation {

  def canCompleteCircuit(gas: Array[Int], cost: Array[Int]): Int  = {

    val len = gas.length

    // This solution checks for all the indexes (which we do NOT want, We want to jump the index)
    gas.indices.foreach{ station =>

      var j = station
      var tank = gas(j) - cost(j)

      while (tank >= 0) {

        j = (j + 1) % len
        tank = tank + (gas(j) - cost(j))

        if (j == station) return j // Exit condition
      }
    }

    -1
  }

  def canCompleteCircuitOpt(gas: Array[Int], cost: Array[Int]): Int  = {

    var stationIterator = 0
    var tank = 0
    val len = gas.length

    while (stationIterator < len) {

      var currentStation = stationIterator
      while (tank >= 0) {

        if (currentStation == stationIterator + len) return stationIterator

        tank = tank + (gas(currentStation % len) - cost(currentStation % len))
        currentStation += 1
      }

      stationIterator = currentStation
      tank = 0
    }

    -1
  }

}

object GasStation extends App {
  val sol = new GasStation()

  // Test 1
  var A = Array[Int](1, 2) // gas
  var B = Array[Int](2, 1) // require
  var data = sol.canCompleteCircuit(A, B)
  assert(data == 1)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.canCompleteCircuit(A)
//  assert(data == 42)

}
