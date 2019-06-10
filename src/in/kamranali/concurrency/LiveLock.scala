package in.kamranali.concurrency

object LiveLock extends App {

  case class Friend(name: String) {
    var side = "right"
    def switchSide() = {
      if (side == "right") side = "left"
      else side = "right"
    }

    def pass(other: Friend) = {
      while (this.side == other.side) {
        val thisSide = this.side
        val otherSide = other.side

        println(s"$this: oh, but please, $other, feel free to pass ... (this.side = $thisSide and other.side = $otherSide)")
        switchSide()
        println(s"$this has switched side")
        Thread.sleep(1000)
      }
    }
  }

  val sam = Friend("Sam")
  val pierre = Friend("Pierre")

  /*
  LiveLock: No thread is block as we don't have synchronisation lock + No thread is
    continued running as they are yielding execution to each other
   */
  new Thread(() => sam.pass(pierre)).start()
  new Thread(() => pierre.pass(sam)).start()

}
