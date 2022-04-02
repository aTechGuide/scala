package in.kamranali.concurrency

/**
  * [Concurrency] Chapter 4: JVM Thread Communication
  * Buffer Between Producer and Consumer
  *
  * This problem is complicated because both the producer and consumer may block each other
  * - When buffer is full, Producer must block until consumer has finished extracting some values
  * - When buffer is Empty, Consumer must block until Producer produces more
  */

import scala.collection.mutable
import scala.util.Random

object Concurrency4_ProducerConsumerFromBuffer extends App {

  def prodConsLargeBuffer(): Unit = {

    // Shared Object
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]()
    val capacity = 3

    // Consumer
    val consumer = new Thread(() => {
      println("[Consumer] Waiting ... ")

      while (true) {
        buffer.synchronized {
          if (buffer.isEmpty) {
            println("[Consumer] buffer empty, waiting ...")
            buffer.wait()
          }

          // there must be at least ONE value in the buffer
          val x = buffer.dequeue()

          println(s"[Consumer] consumed $x")

          // consumer has extracted a value. So in case a producer is sleeping, send a signal
          buffer.notify()
        }

        Thread.sleep(new Random().nextInt(500))
      }
    })

    // Producer
    val producer = new Thread(() => {
      println("[Producer] Computing ...")

      var i = 0 // data to be produced

      while (true) {
        buffer.synchronized {
          if (buffer.size == capacity) {
            println("[Producer] buffer is full, waiting ...")
            buffer.wait()
          }

          // there must be at least one EMPTY space in the buffer
          println("[Producer] producing " + i)
          buffer.enqueue(i)

          // producer has produced a value. So in case a consumer is sleeping, notify it
          buffer.notify()

          i += 1
        } // synchronized ends

        Thread.sleep(new Random().nextInt(500))
      }
    })

    // Driver
    consumer.start()
    producer.start()
  }

  prodConsLargeBuffer()
}
