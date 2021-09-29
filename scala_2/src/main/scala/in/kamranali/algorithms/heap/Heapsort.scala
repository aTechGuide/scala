package in.kamranali.algorithms.heap

/**
  * https://www.interviewbit.com/tutorial/heap-sort-algorithm/
  */
class Heapsort {

  def sort(A: Array[Int]): Unit = {

    val len = A.length

    // Building the Max Heap
    (len/2 - 1 to 0 by -1).foreach { parent =>
      heapify(A, len, parent)
    }

    // Extracting elements from the heap
    (len-1 to 0 by -1).foreach { len =>
      val temp = A(0)
      A(0) = A(len)
      A(len) = temp

      // Call heapify on reduces heap
      heapify(A, len, 0)
    }
  }

  def heapify(A: Array[Int], len: Int, parent: Int): Unit = {

    var largest = parent

    val leftChildIdx = 2 * parent + 1
    val rightChildIdx = 2 * parent + 2

    largest = if (leftChildIdx < len && A(leftChildIdx) > A(largest)) leftChildIdx else largest
    largest = if (rightChildIdx < len && A(rightChildIdx) > A(largest)) rightChildIdx else largest

    // if largest is NOT root
    if (largest != parent) {
      val temp = A(parent)
      A(parent) = A(largest)
      A(largest)= temp

      // Recursively correct the affected sub Tree
      heapify(A, len, largest)
    }
  }
}

object Heapsort extends App {

  val heapsort = new Heapsort()

  val data = Array[Int](22, 21, 3, 25, 26, 7)
  heapsort.sort(data)

  println(data.mkString(" ")) // by 3 7 21 22 25 26
}
