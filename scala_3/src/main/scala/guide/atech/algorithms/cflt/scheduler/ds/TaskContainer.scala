package guide.atech.algorithms.cflt.scheduler.ds

import scala.collection.mutable

class TaskContainer(capacity: Int) {
  private val ordering = Ordering.fromLessThan[Task]((a, b) => (b.delay + b.scheduled) < (a.delay + a.scheduled))
  private val minQueue = mutable.PriorityQueue[Task]()(ordering)

  def addTask(task: Task): Unit = synchronized {

    while (minQueue.size == capacity) { // Post waking up while loop will check
      println(s"[addTask] It seems the queue is full so sleep")
      this.wait() // Release the monitor as we can't enqueue
    }
    
    println(s"[addTask] Find empty slot in Queue adding Task")
    minQueue.enqueue(task)
    this.notifyAll() //
  }

  def fetchTaskToSchedule(boundary: Long): Option[Runnable] = synchronized {
    while (minQueue.isEmpty) {
      println(s"[fetchTaskToSchedule] It seems the queue is Empty so wait")
      this.wait()
    }

    //println(s"[fetchTaskToSchedule] Before head ${minQueue.mkString(";")}")
    val task = minQueue.head
    //println(s"[fetchTaskToSchedule] After head ${minQueue.mkString(";")}")
    val runTime = task.scheduled + task.delay

    val work = if (boundary >= runTime) {
      println(s"[fetchTaskToSchedule] Found a task whose boundary condition is passed so returning it")
      Some(minQueue.dequeue().work)
    } else {
      println(s"[fetchTaskToSchedule] None of the tasks boundary condition is met so returning None")
      None
    }
    
    notifyAll()
    work
  }
}
