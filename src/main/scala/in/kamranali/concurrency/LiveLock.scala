package in.kamranali.concurrency

/**
  * [Concurrency] Chapter 5: JVM Thread Communication
  * Exercise 3 - LiveLock
  */

/**
    LiveLock:
    No thread is block as we don't have synchronisation lock + No thread is
    continued running as they are yielding execution to each other

    A Livelock is also a situation where threads can not continue but
    as oppose to a deadlock a live lock implies that threads yield execution to each other
    in such a way that nobody can continue.
    So the threads are active. They're not blocked but they can now
  */
object LiveLock extends App {

  case class Friend(name: String) {
    var side = "right"

    def switchSide(): Unit = {
      if (side == "right") side = "left"
      else side = "right"
    }

    def pass(other: Friend): Unit = {
      while (this.side == other.side) {

        println(s"$this: oh, but please, $other, feel free to pass ... (this.side = ${this.side} and other.side = ${other.side})")
        println(s"$this has started switching side")
        switchSide()
        println(s"$this has switched side")
        Thread.sleep(1000)
      }
    }
  }

  val sam = Friend("Sam")
  val pierre = Friend("Pierre")


  new Thread(() => sam.pass(pierre)).start()
  new Thread(() => pierre.pass(sam)).start()

}
