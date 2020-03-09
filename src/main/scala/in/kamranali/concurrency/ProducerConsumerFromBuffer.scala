package in.kamranali.concurrency

import scala.collection.mutable
import scala.util.Random

object ProducerConsumerFromBuffer extends App {

  def prodConsLargeBuffer() = {

    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]()
    val capacity = 3

    val consumer = new Thread(() => {
      println("[Consumer Waiting] ... ")

      val random = new Random()

      while (true) {
        buffer.synchronized {
          if (buffer.isEmpty) {
            println("[Consumer] buffer empty, waiting ...")
            buffer.wait()
          }

          // there must be atleast ONE value in the buffer
          val x = buffer.dequeue()

          println("[Consumer] consumed " + x)

          // consumer has extracted a value. So in case a producer is sleeping, send a signal
          buffer.notify()
        }

        Thread.sleep(random.nextInt(500))
      }
    })

    val producer = new Thread(() => {
      println("[producer] computing ...")

      val random = new Random()
      var i = 0

      while (true) {
        buffer.synchronized {
          if (buffer.size == capacity) {
            println("[Producer] buffer is full, waiting ...")
            buffer.wait()
          }

          // there must be atleast one EMPTY space in the buffer
          println("[Producer] producing " + i)
          buffer.enqueue(i)

          // producer has produced a value. So in case a consumer is sleeping, send a signal
          buffer.notify()

          i += 1
        }

        Thread.sleep(random.nextInt(500))
      }
    })

    consumer.start()
    producer.start()

  }

  prodConsLargeBuffer()

}
