package guide.atech.algorithms.cflt.scheduler.ds

case class Task(id: Int, work: Runnable, scheduled: Long, delay: Long)
