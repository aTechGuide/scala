package guide.atech.algorithms.team.connection_pool

import scala.util.Try

trait ConnectionPool[T] {
  def getConnection: Try[T]
  def releaseConnection(connection: T): Unit
}
