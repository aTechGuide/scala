package in.kamranali.lm.multithreading

object Basics1 {

  /*
    [LM Threading Chapter 1] Multithreading concepts with Examples and Code
   */

  class SharedObject extends Runnable {

    val lock = "lock"

    // Instance Level Synchronization
    override def run(): Unit = { // Critical Section Starts

      // We can synchronise on any object, but it should be shared object between both the threads
      // Shown below two options

      // Option 1
      lock.synchronized {
        // Only a single thread will enter here
        println(s"Thread${Thread.currentThread().getName} entered :I'm thread safe")
      }

      // Option 2
      this.synchronized {
        println(s"Thread${Thread.currentThread().getName} entered :I'm thread safe")
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val sharedObject = new SharedObject()

    val t1 = new Thread(sharedObject)
    val t2 = new Thread(sharedObject)

    t1.start()
    t2.start()
  }
}
