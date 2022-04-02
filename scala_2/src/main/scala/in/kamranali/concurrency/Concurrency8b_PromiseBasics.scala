package in.kamranali.concurrency


/**
  * [Concurrency] Chapter 8 B: Future And Promises
  *
  * Promises
  * Manual manipulation of futures with promises
  */

import scala.concurrent.Promise
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

object Concurrency8b_PromiseBasics extends App {

  /**
    Futures are Read Only when they are done. Sometimes we need to specifically set or complete a Future at the point of our choosing.

    Which is a NEED, introducing the concept of

    PROMISES: Which are a "Writable-once" containers / managers over a Future
   */

  // We can assume Promise as Controller over future
  val promise = Promise[Int]() // `apply` method
  val future = promise.future // Promise has a member `future` which it holds and manages

  // Thread 1 Consumer
  // This thread knows how to handle the future
  future.onComplete( {
    case Success(value) => println(s"[consumer] I've recieved $value")
  })

  // Thread 2 Producer
  // This thread inserts value/failure into future by `promise.success(42)`
  val producer = new Thread(() => {
    println("[producer] crunching numbers...")
    Thread.sleep(500)

    // "fulfilling" the promise
    promise.success(42) // <- It basically manipulates the internal future to complete with a successful value 42
    // which is then handled in `onComplete` by some consumer thread
    println("[producer] done")
  })

  /**
    * [Promise Pattern]
    * - One thread knows how to handle the future
    * - Another thread inserts values or a failure into the future by calling `promise.success` Or `promise.failure`.
    */

  producer.start()
  Thread.sleep(1000)

  /**
    The producer consumer problem is Simpler now as we do not have any concurrency issues

    Future-Promise paradigm is powerful as it separates the concern of reading/handling futures and writing to a promise
    while eliminating concurrency issues almost completely

    We have almost complete control over when and how we want to set a value to a future
    when we see fit.
   */
}
