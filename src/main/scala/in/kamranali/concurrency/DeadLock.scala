package in.kamranali.concurrency

object DeadLock extends App {


  case class Friend(name: String) {
    def bow(other: Friend) = {
      this.synchronized {
        println(s"$this:I am bowing to my friend $other")
        other.rise(this)
        println(s"$this my friend $other has risen")
      }
    }

    def rise(other: Friend) = {
      this.synchronized {
        println(s"$this I am rising to my friend $other")
      }
    }
  }

  val sam = Friend("Sam")
  val pierre = Friend("Pierre")

  /*
  Deadlock: Two threads locking objects in reverse order

  - If try to acquire monitor on another object within a synchronised block, then BE CAREFUL

   */
  new Thread(() => sam.bow(pierre)).start() // sam's lock first and is waiting to acquire pierre's lock
  new Thread(() => pierre.bow(sam)).start() // pierre's lock first and is waiting to acquire sam's lock

}
