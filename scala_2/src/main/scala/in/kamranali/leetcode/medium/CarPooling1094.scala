package in.kamranali.leetcode.medium

object CarPooling1094 {
  // https://leetcode.com/problems/car-pooling/discuss/317610/JavaC%2B%2BPython-Meeting-Rooms-III
  def carPooling(trips: Array[Array[Int]], capacity: Int): Boolean = {
    import scala.collection.mutable
    val stops = new mutable.TreeMap[Int, Int]() // Based on total trips = [0 <= fromi < toi <= 1000]

    trips.foreach {trip => {
      stops(trip(1)) = stops.getOrElse(trip(1), 0) + trip(0)
      stops(trip(2)) = stops.getOrElse(trip(2), 0) - trip(0)
    }}

    stops.values.foldLeft((true, 0))((accum, value) => {
      val (ans, currCapacity) = accum

      if (!ans) accum
      else {
        val nCapacity = currCapacity + value
        val nAns = if (nCapacity > capacity) false else true
        (nAns, nCapacity)
      }
    })._1
  }

  // https://leetcode.com/problems/car-pooling/discuss/317611/C%2B%2BJava-O(n)-Thousand-and-One-Stops
  def carPooling2(trips: Array[Array[Int]], capacity: Int): Boolean = {
    val stops = Array.ofDim[Int](1001) // Based on total trips = [0 <= fromi < toi <= 1000]

    trips.foreach {trip => {
      stops(trip(1)) = stops(trip(1)) + trip(0)
      stops(trip(2)) = stops(trip(2)) - trip(0)
    }}

    def isPoolingPossible(idx: Int, currCapacity: Int): Boolean = {
      if (idx == 1001) true
      else if (currCapacity > capacity) false
      else {
        val stop = stops(idx)
        isPoolingPossible(idx + 1, currCapacity + stop)
      }
    }

    isPoolingPossible(0, 0)
  }

  def carPooling1(trips: Array[Array[Int]], capacity: Int): Boolean = {
    import scala.collection.mutable.PriorityQueue
    
    val sortOrdering = Ordering.fromLessThan[Array[Int]]((a, b) => a(1) < b(1))
    val sortedTrips = trips.sorted(sortOrdering)
    
    val qOrdering = Ordering.fromLessThan[Array[Int]]((a, b) => b(2) < a(2))
    val minHeap = PriorityQueue[Array[Int]]()(qOrdering)
    
    // Auxiliary Function
    def reduceCapacity(start: Int, reduction: Int = 0): Int = {
      if (minHeap.isEmpty) reduction
      else {
        val popped = minHeap.head
        
        if (popped(2) <= start) { // destination <= currStart, decrease Capacity
          minHeap.dequeue // Remove the element
          reduceCapacity(start, reduction + popped(0))
        } else {
          reduction
        }
      }
    }
    
    def check(idx: Int, currentCapacity: Int): Boolean = {
      if (idx == sortedTrips.length) true
      else {
        // Try reducing Capacity
        
        val currTrip = sortedTrips(idx)
        val currTripCapacity = currTrip(0)
        
        val reduction = reduceCapacity(currTrip(1))
        val adjustedCapacity = currentCapacity - reduction
        
        if (currTripCapacity + adjustedCapacity <= capacity) {
          minHeap.enqueue(currTrip)
          check(idx + 1, currTripCapacity + adjustedCapacity)
        } else false
      }
    }
    
    // Driver
    check(0, 0)
  }

  def main(args: Array[String]): Unit = {
    println(carPooling(Array(
      Array(2,1,5),Array(3,5,7)
    ), 3))
  }
}