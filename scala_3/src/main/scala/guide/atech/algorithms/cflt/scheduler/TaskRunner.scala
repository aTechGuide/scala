package guide.atech.algorithms.cflt.scheduler
import guide.atech.algorithms.cflt.scheduler.ds.TaskContainer

import java.util.concurrent.Executors

class TaskRunner(id: Int, taskContainer: TaskContainer, period: Long) {

  private val executor = Executors.newSingleThreadExecutor()
  def trigger(): Unit = {

    while (true) {
      val task = taskContainer.fetchTaskToSchedule(System.currentTimeMillis())

      task match
        case Some(task) =>
          println(s"Submitting task")
          executor.submit(task)
        case None =>

      if (period > 0)
        Thread.sleep(period)
    }
  }
}
