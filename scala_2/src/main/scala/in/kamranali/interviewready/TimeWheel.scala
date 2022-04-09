package in.kamranali.interviewready

import java.util.UUID
import java.util.concurrent.{ArrayBlockingQueue, ExecutorService, Executors, Future}
import scala.collection.mutable
import scala.util.{Failure, Success, Try}

// Data Structures
case class Request(id: String, data: String, startTime: Int) {
  def compute(): Response = {
    Thread.sleep(200) // Doing expensive Computation
    Response(UUID.randomUUID().toString, "Dummy Data")
  }
}
case class Response(id: String, data: String)

/**
  *
  * Time Wheel Implementation
  * Reference
  * - https://github.com/coding-parrot/Low-Level-Design/blob/master/rate-limiter/src/main/java/TimerWheel.java
  *
  * @param timeOutPeriod: # of Slots on Wheel
  * @param capacityPerSlot: # of requests that can be queued on each slot
  */
class TimeWheel(timeOutPeriod: Int,
                capacityPerSlot: Int) {

  // Initialized Slot
  private val slots = Array.fill[ArrayBlockingQueue[Request]](timeOutPeriod)(new ArrayBlockingQueue[Request](capacityPerSlot))
  private val slotThreads = Array.fill[ExecutorService](timeOutPeriod)(Executors.newSingleThreadExecutor())
  private val requestSlotMap = mutable.Map.empty[String, Int]

  // Add Request to the Current Slot Asynchronously
  def addRequest(request: Request): Future[Try[Response]] = {

    val currentSlot = getCurrentSlot

    this.slotThreads(currentSlot).submit(() => {

      if (slots(currentSlot).size() == capacityPerSlot) Failure(new RuntimeException("Queue is Full"))
      else {
        slots(currentSlot).add(request)
        requestSlotMap.put(request.id, currentSlot)
        Success(request.compute())
      }
    })
  }

  // Removes the top request and processes it
  def evict: Future[Response] = {
    val currentSlot = getCurrentSlot

    this.slotThreads(currentSlot).submit(() => {
      val request = slots(currentSlot).remove()
      requestSlotMap.remove(request.id)
      request.compute()
    })
  }

  // Discard all the requests in current Slot
  def flushAll(): Future[_] = {
    val currentSlot = getCurrentSlot

    this.slotThreads(currentSlot).submit(new Runnable {
      override def run(): Unit = {
        slots(currentSlot).forEach { request => {
          if (System.currentTimeMillis().toInt - request.startTime > timeOutPeriod) {
            slots(currentSlot).remove(request)
            requestSlotMap.remove(request.id)
          }
        }}
      }
    })
  }

  private def getCurrentSlot: Int = System.currentTimeMillis().toInt % slots.length

}
