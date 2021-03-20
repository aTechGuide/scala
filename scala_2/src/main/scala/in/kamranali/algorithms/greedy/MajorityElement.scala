package in.kamranali.algorithms.greedy

/**
  * https://www.interviewbit.com/problems/majority-element/
  */

class MajorityElement {

  def majorityElementOpt(A: Array[Int]): Int  = {

    var majElem = A(0)
    var count = 1

    (1 until A.length).foreach{idx =>

      if (majElem == A(idx)) {
        count += 1
      } else if (count > 1) {
        count -= 1
      } else {
        majElem = A(idx)
      }
    }

    majElem
  }

  def majorityElement(A: Array[Int]): Int  = {

    var majElem = A(0)
    val barrier = math.floor(A.length / 2).toInt

    var counter = Map.empty[Int, Int]

    for (elem <- A) {

      if (counter.contains(elem)) {

        val temp = counter(elem) + 1

        counter -= elem
        counter += (elem -> temp)

        if (counter(elem) > barrier) {
          majElem = elem
        }
      } else {
        counter += (elem -> 1)
      }

    }

    majElem
  }

}

object MajorityElement extends App {
  val sol = new MajorityElement()

  // Test 1
  var A = Array[Int](2, 1, 1)
  var data = sol.majorityElement(A)
  assert(data == 1)

  // Test 2
//  A = Array[Int](-2, 1, -3, 4, -1, 2, 1, -5, 4)
//  data = sol.majorityElement(A)
//  assert(data == 42)

}
