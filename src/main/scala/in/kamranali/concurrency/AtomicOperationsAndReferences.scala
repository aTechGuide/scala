package in.kamranali.concurrency

import java.util.concurrent.atomic.AtomicReference

object AtomicOperationsAndReferences extends App {

  /*
    Atomic Operation: Runs fully Or NOT AT ALL i.e. it can't be intercepted by a thread
   */

  val atomic = new AtomicReference[Int](2)

  val currentValue = atomic.get() // <- Thread Safe READ
  atomic.set(3) // <- Thread Safe Write

  atomic.getAndSet(5) // Thread Safe READ and Write

  atomic.compareAndSet(38, 56) // <- Reference Equality
  // if value = 38 then set it to 56
  // else DO NOTHING

  atomic.updateAndGet(_ + 1) // thread safe function Run "_ + 1" on top of current value + update and returns the value inside Atomic Reference
  atomic.getAndUpdate(_ + 1) // Get old value first and then run the function on the value contained in Atomic Reference

  atomic.accumulateAndGet(12, _ + _) // SUM the value inside `atomic` and 12, set the value inside atomic and return the result
  atomic.getAndAccumulate(12, _ + _ ) // Reverse of above.



}
