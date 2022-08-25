package guide.atech.algorithms.concurrency.examples

import java.util.concurrent.locks.ReentrantLock

// https://docs.oracle.com/javase/tutorial/essential/concurrency/newlocks.html
object AvoidDeadlockViaReentrant {
  class Friend(val name: String) {

    val lock = new ReentrantLock()

    def bow(bower: Friend): Unit = {

      val myLock = lock.tryLock()
      val yourLock = bower.lock.tryLock()

      if (myLock && yourLock) {
        println(s"$name has acquired both the locks: ${bower.name} has bowed to me!")
        bower.bowBack(this)
      } else {
        if (myLock) lock.unlock()
        if (yourLock) bower.lock.unlock()
        println(s"Couldn't acquire one of the lock myLock = $myLock and your lock = $yourLock")
      }

    }

    def bowBack(bower: Friend): Unit = {
      println(s"$name: ${bower.name} has bowed back to me")
      println(s"$name: ${bower.name} releasing the lock for both")
      this.lock.unlock()
      bower.lock.unlock()
    }
  }

  def main(args: Array[String]): Unit = {

    val f1 = Friend("Kamran")
    val f2 = Friend("Daud")

    new Thread(() => f1.bow(f2)).start()
    new Thread(() => f2.bow(f1)).start()

  }

}
