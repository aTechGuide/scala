package in.kamranali.concurrency

import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

object PromiseBasics extends App {

  /*
    Futures are Read Only when they are done. Sometimes we need to specifically set or complete a Future at the point of our choosing.

    Which is a NEED, introducing the concept of

    PROMISES: Which are a "Writable-once" containers over a Future
   */

  val promise = Promise[Int]() // `apply` method
  val future = promise.future

  // Thread 1 Consumer
  // This thread knows how to handle the future
  future.onComplete( {
    case Success(value) => println("[consumer] I've recieved " + value)
  })

  // Thread 2 Producer
  // This thread inserts value/failure into future by `promise.success(42)`
  val producer = new Thread(() => {
    println("[producer] crunching numbers...")
    Thread.sleep(500)

    // "fulfilling" the promise
    promise.success(42)
    println("[producer] done")
  })

  producer.start()
  Thread.sleep(1000)

  /*
    Future-Promise paradigm is powerful as it separates the concern of reading/handling futures and writing to a promise
    while eliminating concurrency issues almost completely
   */



}
