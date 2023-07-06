package guide.atech.algorithms.team.connection_pool

import scala.util.Try

trait ConnectionProvider[T] {
  def createConnection: Try[T]
}
