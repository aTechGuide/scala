package guide.atech.algorithms.team.connectionpool

import guide.atech.algorithms.team.connectionpool.data.Connection
import scala.util.Try

trait ConnectionPool[T] {

  // Returns a connection from this pool if it is available
  def getConnection: Try[T]

  // Returns a connection from this pool if it is available otherwise waits for no more than timeout milliseconds to get a connection
  def getConnection(timeout: Long): Try[T]

  // Returns connection to the pool
  def releaseConnection(connection: T): Unit

  def maxConnections: Int
}
