package in.kamranali.lm.multithreading

case class Incrementer(var num: Int)

/*
  [LM Threading Chapter 4] Print sequence of Natural numbers using Three Threads
*/
object PrintNaturalNumberBy3Thread4 {

  /*
    Thread Thread-0 print the value 0
    Thread Thread-1 print the value 1
    Thread Thread-2 print the value 2
    Thread Thread-0 print the value 3
    Thread Thread-1 print the value 4
    Thread Thread-2 print the value 5
    Thread Thread-0 print the value 6
  */
  case class Lock()
  class Printer(printCondition: Int, lock: Lock, data: Incrementer) extends Runnable {
    override def run(): Unit = {

      while (true) {

        lock.synchronized {
          while (printCondition != (data.num % 3) ) {
            // Till the time condition is met, release the lock and wait
            lock.wait()
          }

          println(s"Thread ${Thread.currentThread().getName} print the value ${data.num}")

          data.num = data.num + 1
          lock.notifyAll()

        }
      }

    }
  }

  def main(args: Array[String]): Unit = {

    val lock = Lock()
    val data = Incrementer(0)

    new Thread(new Printer(0, lock, data)).start()
    new Thread(new Printer(1, lock, data)).start()
    new Thread(new Printer(2, lock, data)).start()

  }

}
