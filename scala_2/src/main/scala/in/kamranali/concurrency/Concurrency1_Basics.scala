package in.kamranali.concurrency

import java.util.concurrent.Executors

/**
  * [Concurrency] Chapter 1: Basics
  *
  * Scala Doc -> https://docs.scala-lang.org/overviews/core/futures.html
  */
object Concurrency1_Basics extends App {

  val aThread = new Thread(() => println("Running in Parallel"))

  /**
  Starting a Thread by `aThread.start()` actually create a JVM Thread which runs on top of OS Thread
    `aThread.start()` => gives the signal to the JVM to start an JVM  Thread which runs on top of OS thread
    JVM makes the JVM thead invokes the run() method of Runnable Interface
    */
  aThread.start()

  aThread.join() // <- This call will block until the thread has finished running


  val threadHello = new Thread(() => (1 to 5).foreach(_ => println("Hello")))
  val threadGoodBye = new Thread(() => (1 to 5).foreach(_ => println("Good Bye")))

  threadHello.start()
  threadGoodBye.start()

  // Different runs in multi threaded environment produces different results.

  /**
    Executors
    - Threads are expensive to start and kill. Solution is to reuse them
   */

  val pool = Executors.newFixedThreadPool(10)
  pool.execute(() => println("something in the thread pool"))

  pool.execute(() => {
    Thread.sleep(1000)
    println("Done after 1 sec")
  })

  pool.execute(() => {
    Thread.sleep(1000)
    println("Almost Done")

    Thread.sleep(1000)
    println("Done after 2 sec")
  })

  //pool.shutdown()
  //pool.execute(() => println("should not appear")) // <- Throws an exception in the calling thread. As Thread pool does NOT accept any more actions
  // println(pool.isShutdown) // <- Returns true

  //pool.shutdownNow() // <- If actions are executing on this thread pool. Then both of the actions will throw an exception

}
