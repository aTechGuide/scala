package in.kamranali.concurrency

/**
  * [Concurrency] Chapter 2: Concurrency Problems
  */
object RaceCondition extends App {

  // Race Condition
  def runInParallel(): Unit = {
    var x = 0

    val thread1 = new Thread(() => x = 1)
    val thread2 = new Thread(() => x = 2)

    thread1.start()
    thread2.start()
    println(x)
  }

  // for (_ <- 1 to 100) runInParallel

  class BankAccount(var amount: Int) {
    override def toString: String = "" + amount
  }

  def buy(account: BankAccount, thing: String, price: Int): Unit = {
    account.amount -= price // <- Not Atomic Operation
//    println("I've bought" + thing)
//    println("My account is now " + account.amount)
  }

  for (_ <- 1 to 100) {
    val account = new BankAccount(50000)

    val thread1 = new Thread(() => buy(account, "Shoes", 3000))
    val thread2 = new Thread(() => buy(account, "Phone", 4000))

    thread1.start()
    thread2.start()

    Thread.sleep(10)

    if (account.amount != 43000) println("BUG !!" + account.amount)

    /**
      If both thread sees same value
      thread1: 50000
        - account = 50000 - 3000 = 47000

      thread2: 50000
        - account = 50000 - 4000 = 46000 <- This thread overwrites the memory of account.amount. We have a BUG
     */
  }

  /**
    Solution

  Option 1. use synchronized on critical path

  Option 2. use @volatile
      - volatile on var or var means all READ/WRITE to it are synchronized
   */

  /**
    * Synchronized
    * - Entering synchronized expression on an object locks the Object's monitor
    *   - Monitor -> It is the data structure internally used by the JVM to keep track of which object
    *                is locked by Which Thread
    *
    * - Only AnyRefs can have synchronized blocks. So primitive types like Int, Boolean do not have synchronized expressions
    *
    * General Tips
    * - Make no assumption about who gets the lock first
    * - keep locking to minimum (for performance)
    * - maintain thread safety at ALL times in parallel application
   */

  // Option 1. Using synchronized
  def buySafe(account: BankAccount, thing: String, price: Int): Unit = {
    account.synchronized {
      // no two threads can evaluate this at same time
      account.amount -= price

      println("I've bought" + thing)
      println("My account is now " + account.amount)
    }
  }

  // Option 2: Using @volatile
  class BankAccountSafe(@volatile var amount: Int) {
    override def toString: String = "" + amount
  }

  /**
    Exercise 1

    Construct 50 "inception" threads
        Thread1 -> thread2 -> thread3 -> ...
        println("hello from thread #3")
      in REVERSE ORDER

   */
  def inceptionThreads(maxThreads: Int, index: Int = 1): Thread  =
    new Thread(() => {
      if (index < maxThreads) {
        val newThread = inceptionThreads(maxThreads, index + 1)
        newThread.start()
        newThread.join()
      }

      // New threads are created and joined before current thread gets chance to print the message
      println(s"Hello from thread $index")
    })

  inceptionThreads(10).start()


  /**
    Exercise 2
    - what's the biggest value possible for x in threads
    - what's the smallest value possible for x in threads

   */

  var x = 0
  val threads = (1 to 100).map(_ => new Thread(() => x += 1))
  threads.foreach(_.start())
  threads.foreach(_.join())
  println(s"x = $x")


  /*
    Solution
    - Biggest value possible for x in threads = 100 (when all the increments are perfect)
    - Smallest value possible for x in threads = 1 (when all the threads read same value = 0)
   */

  /**
    Exercise 3

    what's the value of message in following code?
    is it guaranteed?
    why? why not?

   */
  var message = ""
  val awesomeThread = new Thread(() => {
    Thread.sleep(1000)
    message = "Scala is awesome"
  })

  message = "Scala sucks"
  awesomeThread.start()
  Thread.sleep(2000)
  println(message)

  /*
  Solution

  what's the value of message? almost always "Scala is awesome"
    is it guaranteed? NO!
    why? why not?

    "sleep fallacy" (A very wrong programming practice of synchronizing threads by putting them to sleep)
    Remember => Sleep doesn't guarantee execution sequence / ordering of evaluation

    So the answer to above questions are
    - Value of message is almost always be "Scala is awesome"
    - But it is not guaranteed
   */
  /*
    One probable sequence of execution
      (main thread)
        message = "Scala sucks"
        awesomeThread.start()
        sleep() - relieves execution
      (awesome thread)
        sleep() - relieves execution
      (OS gives the CPU to some important thread - takes CPU for more than 2 seconds)
      (OS gives the CPU back to the MAIN thread)
        println("Scala sucks")
      (OS gives the CPU to awesomethread)
        message = "Scala is awesome"
   */

  /*
    The only way to solve it is through `join` NOT Synchronization
      As Synchronization is ONLY useful for concurrent modifications
   */

  /*
    Solution
   */

  var message1 = ""
  val awesomeThread1 = new Thread(() => {
    Thread.sleep(1000)
    message = "Scala is awesome"
  })

  message1 = "Scala sucks"
  awesomeThread1.start()
  Thread.sleep(2000)

  awesomeThread1.join() // Solution line [Wait for awesome thread to join]
  println(message1)

}
