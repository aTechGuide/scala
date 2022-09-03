package guide.atech.algorithms.team.connectionpool.provider

import guide.atech.algorithms.team.connectionpool.data.Connection
import scala.util.Try

trait ConnectionProvider[T] {
  def createConnection(): Try[T]
}
