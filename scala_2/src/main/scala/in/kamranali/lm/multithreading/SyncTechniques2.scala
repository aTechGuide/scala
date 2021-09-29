package in.kamranali.lm.multithreading

/*
  [LM Threading Chapter 2] How to Achieve the synchronization between Multithreaded Environment
*/
object SyncTechniques2 {

  // Technique 1 [via common lock]
  def main1(args: Array[String]): Unit = {

    val lock = new Object

    val runnable1 = new Runnable {
      override def run(): Unit = lock.synchronized {
        println(s"Thread safe via external lock")
      }
    }

    val runnable2 = new Runnable {
      override def run(): Unit = lock.synchronized {
        println(s"Thread safe via external lock")
      }
    }

    new Thread(runnable1).start()
    new Thread(runnable2).start()

  }

  // Technique 2 [via synchronised method]
  def main2(args: Array[String]): Unit = {

    class CommonClass() {
      def function(): Unit = this.synchronized {
        println(s"Thread safe via synchronized")
      }
    }

    val obj = new CommonClass

    new Thread(() => {obj.function() }).start() // T1
    new Thread(() => {obj.function() }).start() // T2
  }

}
