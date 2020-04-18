package in.kamranali.concurrency.questions

/**
  * [Concurrency] Question 1: https://leetcode.com/problems/print-in-order
  */

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object PrintInOrder {

  object Foo {

    val lock = new Object
    var x = 0

    def method1 = {

      lock synchronized  {
        println(1)
        x += 1
        lock.notifyAll()
      }
    }

    def method2 = {

      lock synchronized  {
        while (x != 1) {
          lock.wait()
        }

        println(2)
        x += 1
        lock.notifyAll()
      }
    }

    def method3 = {
      lock synchronized  {
        while (x != 2) {
          lock.wait()
        }

        println(3)
        lock.notifyAll()
      }
    }

    def method4 = {
      Thread.sleep(500)
      println(4)
      4
    }

    def method5 = {
      Thread.sleep(500)
      println(5)
      5
    }

    def method6 = {
      Thread.sleep(500)
      println(6)
      6
    }
  }

  private def threadSolution(): Unit = {

    val th1 = new Thread(() => {
      Thread.sleep(500)
      Foo.method1
    }).start()

    val th2 = new Thread(() => {
      Thread.sleep(500)
      Foo.method2
    }).start()

    val th3 = new Thread(() => {
      Thread.sleep(500)
      Foo.method3
    }).start()
  }

  private def futureSolution: Unit = {
    val fut1 = Future {
      Foo.method1
    }

    val fut2 = Future {
      Foo.method2
    }

    val fut3 = Future {
      Foo.method3
    }
  }

  def main(args: Array[String]): Unit = {

    threadSolution
    //futureSolution
  }

}
