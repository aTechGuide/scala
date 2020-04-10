package in.kamranali.concurrency.questions

import java.util.concurrent.Executors

import scala.collection.mutable

/**
  *
  * Reference: http://www.devinline.com/2016/01/print-pattern-1-2-3-using-three-threads.html
  */
case class Consumer(sequence: Int) extends Runnable {

  import Consumer._

  override def run(): Unit = {

    while (true) {

      // Adding sleep to slow down the processing
      Thread.sleep(500)

      data.synchronized {

        // First Thread handled here
        if (sequence == 1) {
          if (!one) {
            data.wait()
          } else {

            if (iterator != capacity) {
              println(s"Thread $sequence Consumed ${data(iterator)}")
              iterator += 1
            } else {
              println(s"Thread $sequence has not consumed anything. Data is empty")
            }


            one = false
            two = true // We are signalling second thread to proceed
            three = false
            data.notifyAll()
          }
        }

        // Second Thread handled here
        if (sequence == 2) {
          if (!two) {
            data.wait()
          } else {

            if (iterator != capacity) {
              println(s"Thread $sequence Consumed ${data(iterator)}")
              iterator += 1
            } else {
              println(s"Thread $sequence has not consumed anything. Data is empty")
            }

            one = false
            two = false
            three = true // We are signalling third thread to proceed
            data.notifyAll()
          }
        }

        // Third Thread handled here
        if (sequence == 3) {
          if (!three) {
            data.wait()
          } else {

            if (iterator != capacity) {
              println(s"Thread $sequence Consumed ${data(iterator)}")
              iterator += 1
            } else {
              println(s"Thread $sequence has not consumed anything. Data is empty")
            }

            one = true // We are signalling first thread to proceed
            two = false
            three = false
            data.notifyAll()
          }
        }

      }
    }
  }
}

object Consumer {

  // This will facilitate synchronization between threads
  var one: Boolean = true // Initially we will let first thread to proceed
  var two: Boolean = false
  var three: Boolean = false

  // This is the data that we are consuming via multiple threads. We will take lock on this object
  val data: mutable.ListBuffer[Int] = mutable.ListBuffer(1, 2, 3, 4, 5, 6, 7, 8, 9)
  var iterator = 0
  private val capacity = data.length //<- Exit Condition

}

object ThreadSequencing {

  def main(args: Array[String]): Unit = {

    val consumer1 = new Thread(Consumer(1))
    val consumer2 = new Thread(Consumer(2))
    val consumer3 = new Thread(Consumer(3))

    val pool = Executors.newFixedThreadPool(3)
    pool.execute(consumer1)
    pool.execute(consumer2)
    pool.execute(consumer3)

    /**
      * Following output will be printed
      *
      *   Thread 1 Consumed 1
      *   Thread 2 Consumed 2
      *   Thread 3 Consumed 3
      *   Thread 1 Consumed 4
      *   Thread 2 Consumed 5
      *   Thread 3 Consumed 6
      *   Thread 1 Consumed 7
      *   Thread 2 Consumed 8
      *   Thread 3 Consumed 9
      *   Thread 1 has not consumed anything. Data is empty
      *   Thread 2 has not consumed anything. Data is empty
      *   Thread 3 has not consumed anything. Data is empty
      *   Thread 1 has not consumed anything. Data is empty
      *   Thread 2 has not consumed anything. Data is empty
      *   Thread 3 has not consumed anything. Data is empty
      *   Thread 1 has not consumed anything. Data is empty
      */
  }

}
