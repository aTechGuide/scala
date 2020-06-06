package in.kamranali.algorithms.heap

/**
  * https://www.interviewbit.com/tutorial/heap-sort-algorithm/
  */
class Heapsort {

  def sort(A: Array[Int]): Unit = {

    val n = A.length

    // Building the Heap
    (n/2 - 1 to 0 by -1).foreach { i =>
      heapify(A, n, i)
    }

    // Extracting elements from the heap
    (n-1 to 0 by -1).foreach { i =>
      val temp = A(0)
      A(0) = A(i)
      A(i) = temp

      // Call heapify on reduces heap
      heapify(A, i, 0)
    }
  }

  def heapify(A: Array[Int], n: Int, parent: Int): Unit = {

    var largest = parent

    val leftChild = 2*parent + 1
    val rightChild = 2*parent + 2

    largest = if (leftChild < n && A(leftChild) > A(largest)) leftChild else largest
    largest = if (rightChild < n && A(rightChild) > A(largest)) rightChild else largest

    // if largest is NOT root
    if (largest != parent) {
       val temp = A(parent)
      A(parent) = A(largest)
      A(largest)= temp

      // Recursively correct the affected sub Tree
      heapify(A, n, largest)
    }
  }
}

object Heapsort extends App {

  val heapsort = new Heapsort()

  val data = Array[Int](22, 21, 3, 25, 26, 7)
  heapsort.sort(data)

  println(data.mkString(" ")) // by 3 7 21 22 25 26
}
