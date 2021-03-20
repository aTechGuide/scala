package in.kamranali.concurrency

/**
  * [Concurrency] Chapter 6: Future And Promises
  *
  */

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object FutureBasics extends App {

  /**
    - Futures are Functional way of computing something in parallel or on another thread
    - Future is a computation that will hold a value, computed by a thread at some point in time.

    Basically, they are IMMUTABLE READ ONLY objects
   */

  def calculateTheMeaningOfLife(): Int = {
    Thread.sleep(2000)
    42
  }

  // Let's Create Future object by calling `apply` method on companion object of `Future` trait
  val aFuture: Future[Int] = Future { // same as `val aFuture = Future(calculateTheMeaningOfLife)`
    calculateTheMeaningOfLife() // <= This calculated the meaning of life on ANOTHER thread
  } // `global` is passed here

  println(aFuture.value) // Option[Try[Int]] as Future might not have finished OR have returned an exception

  println("waiting on the future")

  // NON Blocking Processing
  // Callback inside "onComplete" is called by some thread. It may be "this" thread that created the
  // callback, it may be the thread who ran the future, it may be some other thread
  // So we do NOT make any assumption on which thread will actually execute the partial function inside "onComplete"
  aFuture.onComplete(t => t match {
    case Success(meaningOfLife) => println(s"The meaning of life is $meaningOfLife")
    case Failure(exception) => println(s"I have failed with $exception")
  }) // `global` is passed here

  Thread.sleep(3000)

}
