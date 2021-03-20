package in.kamranali.concurrency

/**
  * [Concurrency] Chapter 9: Future And Promises
  *
  * Exercises
  */

import scala.concurrent.{Future, Promise}
import scala.util.{Random, Try}
import scala.concurrent.ExecutionContext.Implicits.global

object FuturePromiseExercises {

  // Exercise 1: Fulfill a future IMMEDIATELY with a value
  def fulfillImmediately[T](value: T): Future[T] = Future(value)

  // Exercise 2: Second future runs when first has completed i.e. in Sequence
  def inSequence[A, B](first: Future[A], second: Future[B]): Future[B] = {
    first.flatMap(_ => second) // <- returns second future making sure first has run
  }

  // Exercise 3: Return a future containing the earliest value returned by two futures.
  //  first(fa, fb) => new future which will hold
  //  either the value of a if a finishes first or the value of b if b finishes first
  def first[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val promise = Promise[A]

    fa.onComplete(promise.tryComplete)
    fb.onComplete(promise.tryComplete)

    promise.future
  }

  // Exercise 4: last(fa, fb) => new future with the last value of the two futures
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

  // Exercise 5: retryUntil
  // I want you to run an action repeatedly until a condition is met.
  // and return the first value that satisfies the condition.
  def retryUntil[A](action: () => Future[A], condition: A => Boolean): Future[A] =
    action()
      .filter(condition)
      .recoverWith {
        case _ => retryUntil(action, condition)
      }

  val random = new Random()
  val action: () => Future[Int] = () => Future {
    Thread.sleep(100)
    val nextValue = random.nextInt(100)
    println("generated nextValue = " + nextValue)
    nextValue
  }

  retryUntil(action, (x: Int) => x < 50).foreach(result => println("settled at " + result))

  Thread.sleep(10000)

}
