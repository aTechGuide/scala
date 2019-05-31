package in.kamranali.concurrency

object ProducerConsumer extends App {

  class SimpleContainer {
    private var value: Int = 0

    def isEmpty = value == 0

    def set(newValue: Int) = value = newValue

    def get = {
      val result = value
      value = 0
      result
    }
  }

  def naiveProdCons() = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("[Consumer Waiting] ... ")
      while (container.isEmpty) { // Busy Waiting
        println("[Consumer Actively Waiting]")
      }

      println("[Consumer] I have consumed " + container.get)
    })

    val producer = new Thread(() => {
      println("[producer] computing ...")
      Thread.sleep(500)

      val value = 42
      println("[producer] I have produced value: " + value)
      container.set(value)
    })

    consumer.start()
    producer.start()
  }

  // naiveProdCons()

  /*
  Wait & Notify
    - Only allowed in Synchronized expressions else it will crash the program

  Wait: Waiting on an object's monitor suspends the thread indefinitely
   */

  /*
  val someObject = "hello"

  // Thread 1
  // (1)
  someObject.synchronized { // <- lock the object's monitor
    // code part 1

    // (2)
    someObject.wait() // <- Release the lock and wait

    // (6)
    // code part 2 // <- When allowed to proceed, lock the monitor again and continue
  }

  // Thread 2
  // (3)
  someObject.synchronized { // <- lock the object's monitor
    // code part 1

    someObject.notify() // <- Signal ONE sleeping thread that they may continue (WHICH THREAD ?? We don't know). But I'm still holding the lock.


    // code part 2
  } // (5) // <- But only after I'm done and unlocks the monitor

*/

  // No more busy waiting
  def smartProdCons() = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("[Consumer Waiting] ... ")

      container.synchronized {
        container.wait() // <- release the lock and suspends until someone else will signal that they may continue
      }

      // container must have some value
      println("[Consumer] I have consumed " + container.get)
    })

    val producer = new Thread(() => {
      println("[producer] computing ...")
      Thread.sleep(500)

      val value = 42

      container.synchronized {
        println("[producer] I have produced value: " + value)
        container.set(value)
        container.notify()
      }
    })

    consumer.start()
    producer.start()
  }

  smartProdCons()

}
