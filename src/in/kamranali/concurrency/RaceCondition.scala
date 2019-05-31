package in.kamranali.concurrency

object RaceCondition extends App {

  // Race Condition
  def runInParallel = {
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

    /*
    If both thread sees same value
    thread1: 50000
      - account = 50000 - 3000 = 47000

    thread2: 50000
      - account = 50000 - 4000 = 46000 <- This thread overwrites the memory of account.amount. We have a BUG
     */
  }

  /*
  Solution

  1. use synchronized on critical path
      - Only AnyRefs can have synchronized blocks
      - Make no assumption about who gets the lock first
      - keep locking to minimum (for performance)
      - maintain thread safety at ALL times in parallel application

  2. use @volatile
      volatile on var or var means all READ/WRITE to it are synchronized
   */

  // use synchronized
  def buySafe(account: BankAccount, thing: String, price: Int) = {
    account.synchronized {
      // no two threads can evaluate this at same time
      account.amount -= price

      println("I've bought" + thing)
      println("My account is now " + account.amount)
    }
  }

  // use @volatile
  class BankAccountSafe(@volatile var amount: Int) {
    override def toString: String = "" + amount
  }

  /*
  Exercise 1

  Print in reverse value of thread
   */
  def inceptionThreads(maxThreads: Int, index: Int = 1): Thread  = new Thread(() => {
    if (index < maxThreads) {
      val newThread = inceptionThreads(maxThreads, index + 1)
      newThread.start()
      newThread.join()
    }
    println(s"Hello from thread $index")
  })

  inceptionThreads(10).start()


  /*
  Exercise 2
  - Biggest value possible for x in threads = 100 (when all the increments are perfect)
  - Smallest value possible for x in threads = 1 (when all the threads read sane value = 0)

   */

  var x = 0
  val threads = (1 to 100).map(_ => new Thread(() => x += 1))

  /*
  sleep fallacy
  - Value of message is almost always be "Scala is awesome"
  - But it is not guaranteed
   */
  var message = ""
  val awesomeThread = new Thread(() => {
    Thread.sleep(1000)
    message = "Scala is awesone"
  })

  message = "Scala sucks"
  awesomeThread.start()
  Thread.sleep(2000)
  println(message)

  // Sleep doesn't guarantee execution sequence / ordering of evaluation

  // The only way to solve it is through `join` NOT Synchronization

}
