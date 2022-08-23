package guide.atech.algorithms.team.router.model

sealed trait Task {
  def trigger(metadata: Map[String, String]): Unit
}

class BasicTask() extends Task {
  override def trigger(metadata: Map[String, String]): Unit = {
    println(metadata.getOrElse("message", "Some default message"))
  }
}
