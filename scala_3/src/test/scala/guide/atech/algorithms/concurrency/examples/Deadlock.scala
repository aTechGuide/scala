package guide.atech.algorithms.concurrency.examples

object Deadlock {
  case class Friend(name: String) {

    def bow(bower: Friend): Unit = this.synchronized {
      println(s"$name: ${bower.name} has bowed to me!")
      bower.bowBack(this)
    }

    def bowBack(bower: Friend): Unit = this.synchronized {
      println(s"$name: ${bower.name} has bowed back to me")
    }
  }

  def main(args: Array[String]): Unit = {

    val f1 = Friend("Kamran")
    val f2 = Friend("Ali")

    // When Deadlock runs, it's extremely likely that both threads will block when they attempt to invoke bowBack.
    // Neither block will ever end, because each thread is waiting for the other to exit bow.
    new Thread(() => f1.bow(f2)).start()
    new Thread(() => f2.bow(f1)).start()

  }

}
