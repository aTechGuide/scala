package guide.atech.algorithms.cflt.scheduler

import guide.atech.algorithms.cflt.scheduler.ds.Task
import guide.atech.algorithms.cflt.scheduler.ds.TaskContainer
import guide.atech.algorithms.cflt.scheduler.TaskProducer
import guide.atech.algorithms.cflt.scheduler.TaskRunner

class SchedulerSpec extends munit.FunSuite {

  test("Should be able to run 5 tasks") {

    val minutes = 60 * 1000
    val seconds = 1000

    val taskList = (1 to 3).map { idx =>
      val currentTime = System.currentTimeMillis()

      val delay = idx * minutes
      Task(idx, () => println(s"Work 1 done with $idx minutes"), currentTime, delay)
    }.toList

    println(s"Created Tasks $taskList")

    val container = TaskContainer(2)
    val producer = TaskProducer(1, container)

    // Submitting the task in new Thread
    new Thread(() => {
      taskList.foreach { task => {
        println(s"Try Submitting task with id = ${task.id}")
        producer.submit(task)
        println(s"Submitted task with id ${task.id} sleeping for 100 ms")
        Thread.sleep(100)
      }}

      println(s"All the tasks are submitted, producer is ending")
    }).start()

    val runner = TaskRunner(1, container, 20L * seconds)
    println(s"Triggering the runner")
    runner.trigger()
  }

}
