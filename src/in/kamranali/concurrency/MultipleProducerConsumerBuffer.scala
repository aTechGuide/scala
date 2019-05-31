package in.kamranali.concurrency

import scala.collection.mutable
import scala.util.Random

object MultipleProducerConsumerBuffer extends App {

  class Consumer(id: Int, buffer: mutable.Queue[Int]) extends Thread {

    override def run(): Unit = {
      println("[Consumer Waiting] ... ")

      val random = new Random()

      while (true) {
        buffer.synchronized {

          /*
          Condition 1

          Producer produces value and 2 consumers are waiting
          - producer calls notify NOTIFYING ONE of the consumers
          - ONE of the consumers will wake up and will then notify ONE of the THREAD (Watch NOT Producer) waiting on buffer that they may continue
          - Suppose JVM wakes up the OTHER consumer
           */
          while (buffer.isEmpty) { // <- Changing `if` to `while` => when I wake up AND the buffer is not empty
            println(s"[Consumer $id] buffer empty, waiting ...")
            buffer.wait()
          }

          // there must be atleast ONE value in the buffer
          val x = buffer.dequeue() // <- This is wrong with `if` clause on (buffer.isEmpty)

          println(s"[Consumer $id] consumed " + x)

          // Notify anybody waiting on buffer
          buffer.notify() // Or buffer.notifyAll() <- Same behaviour
        }

        Thread.sleep(random.nextInt(500))
      }
    }
  }

  class Producer(id: Int, buffer: mutable.Queue[Int], capacity: Int) extends Thread {
    override def run(): Unit = {
      println(s"[producer $id] computing ...")

      val random = new Random()
      var i = 0

      while (true) {
        buffer.synchronized {
          while (buffer.size == capacity) { // <- Changing `if` to `while` => when I wake up AND the buffer is not FULL
            println(s"[Producer $id] buffer is full, waiting ...")
            buffer.wait()
          }

          // there must be atleast one EMPTY space in the buffer
          println(s"[Producer $id] producing " + i)
          buffer.enqueue(i)

          // Notify anybody waiting on buffer
          buffer.notify() // Or buffer.notifyAll() <- Same behaviour


          i += 1
        }

        Thread.sleep(random.nextInt(500))
      }
    }
  }

  def multiProdCons(nConsumers: Int, nProducers: Int) = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]()
    val capacity = 3

    (1 to nConsumers).foreach(i => new Consumer(i, buffer).start())
    (1 to nProducers).foreach(i => new Producer(i, buffer, capacity).start())
  }

  // multiProdCons(3,3)

  /*
  Exercise
   */

  // Usage of NotifyAll
  def testNotifyAll = {
    val bell = new Object

    (1 to 10).foreach(i => new Thread(() => {
      bell.synchronized {

        println(s"[thread $i] waiting ... ")
        bell.wait()
        println(s"[thread $i] Hooray! ... ")
      }
    }).start())

    new Thread(() => {
      Thread.sleep(2000)

      println("[announcer] Rock'n roll")
      bell.synchronized {
        bell.notifyAll() // <- wakes all the thread waiting on bell
        // bell.notify() // <- wakes only ONE thread
      }
    }).start()

  }

  testNotifyAll

}
