package in.kamranali.algorithms.backtracking

import scala.collection.mutable.ArrayBuffer

/**
  * Reference
  * - https://www.youtube.com/watch?v=KU7Ae2513h0
  */

class IPAddressDecomposition {

  def restoreIpAddresses(s: String): List[String] = {

    def isValidCandidate(candidate: String): Boolean =
      if (candidate.toInt > 255 || ( candidate.length >= 2 && candidate.charAt(0) == '0')) false
      else true

    def bt(input: String, segment: Int, pos: Int, path: Array[Int], result: ArrayBuffer[String]): Unit = {

      if (segment == 4 && pos == input.length) {
        result.append(path.mkString("."))
      } else if (segment == 4 || pos == input.length) {
        // Do Nothing
        ()
      } else {
        (1 to 3).foreach { len =>

          val isValidLen = pos + len <= input.length

          isValidLen match {
            case true =>
              val candidate = input.slice(pos, pos + len)

              if (isValidCandidate(candidate)) {

                // Make a Choice
                path(segment) = candidate.toInt

                // Explore
                bt(input, segment + 1, pos + len, path, result)

                // Unchoose
                path(segment) = -1
              }
            case false =>
          }
        }
      }

    }

    val res = ArrayBuffer[String]()
    val pt = Array.fill[Int](4)(0)

    bt(s, 0, 0, pt, res)

    res.toList
  }

}

object IPAddressDecomposition extends App {
  val sol = new IPAddressDecomposition()

  // Test 1
  var A = "25525511135"
  // var A = "1111"
  var data = sol.restoreIpAddresses(A)
  println(data)
//  assert(data == 42s)
}

