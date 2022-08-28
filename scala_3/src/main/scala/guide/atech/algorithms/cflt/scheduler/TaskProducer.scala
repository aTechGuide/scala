package guide.atech.algorithms.cflt.scheduler

import guide.atech.algorithms.cflt.scheduler.ds.{Task, TaskContainer}

class TaskProducer(val id: Int, taskContainer: TaskContainer) {

  def submit(task: Task): Unit = {
    taskContainer.addTask(task)
  }
}
