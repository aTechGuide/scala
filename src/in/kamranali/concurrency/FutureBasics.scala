package in.kamranali.concurrency

import scala.concurrent.{Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Random, Success, Try}

object FutureBasics extends App {

  /*
    Future is a computation that will hold a value, computed by a thread at some
    point in time.
   */

  def calculateTheMeaningOfLife: Int = {
    Thread.sleep(2000)
    42
  }

  // Let's Create Future object by calling `apply` method on companion object of `Future` trait
  val aFuture = Future { // same as `val aFuture = Future(calculateTheMeaningOfLife)`
    calculateTheMeaningOfLife // <= This calculated the meaning of life on another thread
  } // `global` is passed here

  println(aFuture.value) // Option[Try[Int]] as Future might not have finished OR have returned an exception

  println("waiting on the future")
  aFuture.onComplete(t => t match {
    case Success(meaningOfLife) => println(s"The meaning of life is $meaningOfLife")
    case Failure(exception) => println(s"I have failed with $exception")
  }) // `global` is passed here

  Thread.sleep(3000)

  /*
  Exercise
   */

  // Fulfill a future IMMEDIATELY with a value
  def fulfillImmediately[T](value: T): Future[T] = Future(value)

  // Second future runs when first has completed i.e. inSqquence
  def inSequence[A, B](first: Future[A], second: Future[B]): Future[B] = {
    first.flatMap(_ => second) // <- returns second future making sure first has run
  }

  // first(fa, fb) => new future with the first value of the two futures
  def first[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val promise = Promise[A]

    fa.onComplete(promise.tryComplete)
    fb.onComplete(promise.tryComplete)

    promise.future
  }

  // last(fa, fb) => new future with the last value of the two futures
  def last[A](fa: Future[A], fb: Future[A]): Future[A] = {
    /*
      Two Promises
      - 1st promise which both futures will try to complete
      - 2nd promise which the LAST future will complete
     */
    val bothPromise = Promise[A]
    val lastPromise = Promise[A]

    val checkAndComplete = (result: Try[A]) =>
      if (!bothPromise.tryComplete(result))
        lastPromise.complete(result)

    fa.onComplete(checkAndComplete)
    fb.onComplete(checkAndComplete)

    lastPromise.future
  }

  val fast = Future {
    Thread.sleep(100)
    41
  }

  val slow = Future {
    Thread.sleep(200)
    45
  }

  first(fast, slow).foreach(println)
  last(fast, slow).foreach(println)

  Thread.sleep(1000)

  // retryUntil
  def retryUntil[A](action: () => Future[A], condition: A => Boolean): Future[A] =
    action()
    .filter(condition)
    .recoverWith {
      case _ => retryUntil(action, condition)
    }

  val random = new Random()
  val action = () => Future {
    Thread.sleep(100)
    val nextValue = random.nextInt(100)
    println("generated nextValue = " + nextValue)
    nextValue
  }

  retryUntil(action, (x: Int) => x < 50).foreach(result => println("setteled at " + result))

  Thread.sleep(10000)

}
