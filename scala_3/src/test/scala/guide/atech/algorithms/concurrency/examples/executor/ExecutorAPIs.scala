package guide.atech.algorithms.concurrency.examples.executor

import java.util.concurrent.{Executor, ExecutorService, Executors, ScheduledExecutorService}

object ExecutorInterfaces {

  val executor: Executor = ??? // Interface
  val executorService: ExecutorService = ??? // sub Interface of Executor
  val scheduledExecutorService: ScheduledExecutorService = ??? // sub Interface of ExecutorService

}

object ExecutorAPIs {

  def main(args: Array[String]): Unit = {
    val executor = Executors.newFixedThreadPool(3)
    executor.submit(new Runnable {
      override def run(): Unit = println(s"From inside executor")
    })
  }
}
