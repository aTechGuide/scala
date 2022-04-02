package in.kamranali.concurrency

/**
  * [Concurrency] Chapter 3: JVM Thread Communication
  */

/**
  *  Problem => Forcing the thread to run actions in a guaranteed certain order. Although the thread themselves are NOT ordered
  */
object Concurrency3_ProducerConsumer extends App {

  class SimpleContainer {
    private var value: Int = 0

    def isEmpty: Boolean = value == 0

    /* Producing Method */
    def set(newValue: Int): Unit = value = newValue

    /* Consuming Method */
    def get: Int = {
      val result = value
      value = 0
      result
    }
  }

  def naiveProdCons(): Unit = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("[Consumer Waiting] ... ")
      while (container.isEmpty) { // Busy Waiting
        println("[Consumer Actively Waiting]")
      }

      println(s"[Consumer] I have consumed ${container.get}")
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

  /**

    Wait & Notify
    - Theory in One Note
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
  def smartProdCons(): Unit = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("[Consumer Waiting] ... ")

      container.synchronized {
        container.wait() // <- release the lock and suspends until someone else will signal that they may continue
        // ^^^^^^^^^^^^^ [17 March 2022] I think this is wrong, we should first check and then wait.
        // Its possible that producer has produced some value.
      }

      // container must have some value
      // Because only one that can wake consumer up from waiting will be the producer
      println(s"[Consumer] I have consumed ${container.get}" )
    })

    val producer = new Thread(() => {
      println("[producer] computing ...")
      Thread.sleep(2000)

      val value = 42

      container.synchronized {
        println(s"[producer] I have produced value: $value")
        container.set(value)
        container.notify()
      }
    })

    consumer.start()
    producer.start()
  }

  smartProdCons()

}
