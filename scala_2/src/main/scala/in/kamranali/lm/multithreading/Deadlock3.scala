package in.kamranali.lm.multithreading

/*
  [LM Threading Chapter 3] How to Create Deadlock and Remove it in Multithreaded Environment
*/

case class Lock()
object Deadlock {

  /*
  To create deadlock, write nested synchronize blocks

  Logs when Deadlock occurs
    Thread: Thread-1 took lock2
    Thread: Thread-0 took lock1

  To remove deadlock, acquire locks in order for e.g. both person first acquire lock1, then lock2 or vice versa
   */
  class Person1(lock1: Lock, lock2: Lock) extends Runnable {

    def run(): Unit = {

      lock1.synchronized {

        println(s"Thread: ${Thread.currentThread().getName} took lock1")
        lock2.synchronized {

          println(s"Thread: ${Thread.currentThread().getName} took lock2")
        }
      }
    }
  }

  class Person2(lock1: Lock, lock2: Lock) extends Runnable {

    def run(): Unit = {

      lock2.synchronized {

        println(s"Thread: ${Thread.currentThread().getName} took lock2")
        lock1.synchronized {

          println(s"Thread: ${Thread.currentThread().getName} took lock1")
        }
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val lock1 = new Lock
    val lock2 = new Lock

    new Thread(new Person1(lock1, lock2)).start()
    new Thread(new Person2(lock1, lock2)).start()
  }
}
