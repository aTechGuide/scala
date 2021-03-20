package in.kamranali.concurrency.questions


import scala.concurrent.{ExecutionContext, Future}
import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.blocking
import scala.util.{Failure, Success}

/**
  * [Concurrency] Question 2: https://leetcode.com/problems/print-foobar-alternately/
  */

object PrintAlternatively {

  class FooBar {

    var toggle = false

    def printFoo(): Unit = this.synchronized {

      while (toggle) this.wait()

      print("Foo")
      toggle = !toggle
      notifyAll()
    }

    def printBar(): Unit = this.synchronized {

      while (!toggle) this.wait()

      print("Bar")
      toggle = !toggle
      notifyAll()
    }
  }

  def main(args: Array[String]): Unit = {

    val fooBar = new FooBar

    (0 to 8).foreach(_ => Future {
      blocking {
        Thread.sleep(100)
        fooBar.printFoo()
      }
    })

    (0 to 8).foreach(_ => Future{
      blocking {
        Thread.sleep(100)
        fooBar.printBar()
      }
    })

    println(System.getProperty("scala.concurrent.context.numThreads", "x1"))
    println(System.getProperty("scala.concurrent.context.maxExtraThreads", "256"))


    Thread.sleep(5000)
  }

}
