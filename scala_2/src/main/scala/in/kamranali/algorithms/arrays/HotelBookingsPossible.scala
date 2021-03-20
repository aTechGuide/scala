package in.kamranali.algorithms.arrays

/**
  * https://www.interviewbit.com/problems/hotel-bookings-possible/
  */

class HotelBookingsPossible {

  def solve(A: Array[Int], B: Array[Int], K: Int): Int  = {

    val intervals = List[Tuple2[Int, Int]]()

    val foldedArrival = A.foldLeft(List[(Int, Int)]())((acc, value) => acc :+ (value, 1))
    val foldedDeparture = B.foldLeft(List[(Int, Int)]())((acc, value) => acc :+ (value, 0))

    val booking = foldedArrival ++ foldedDeparture
    val sorted = booking.sorted

    var guests = 0

    for (room <- sorted) {

      guests =
        if (room._2 == 1) guests + 1
        else guests - 1

      if (guests > K) return 0
    }

    1
  }

}

object HotelBookingsPossibleTest extends App {
  val sol = new HotelBookingsPossible()

  // Test 1
  var A = Array[Int](1, 3, 4)
  var B = Array[Int](12, 8, 6)
  var K = 2
  var data = sol.solve(A, B, K)
  assert(data == 0)

  // Test 2
  A = Array[Int](1, 2, 3, 4)
  B = Array[Int](10, 2, 6, 14)
  K = 2
  data = sol.solve(A, B, K)
  assert(data == 0)

  // Test 3
  A = Array[Int](1, 2, 3, 4)
  B = Array[Int](10, 2, 6, 14)
  K = 4
  data = sol.solve(A, B, K)
  assert(data == 1)

}
