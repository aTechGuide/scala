package guide.atech.algorithms.connectionpool.pools

import guide.atech.algorithms.connectionpool.data.Connection
import guide.atech.algorithms.connectionpool.{ConnectionPool, FixedConnectionPool}
import guide.atech.algorithms.connectionpool.provider.JDBCConnectionProvider

class JDBCConnectionPool private (val connectionPool: ConnectionPool[Connection])

object JDBCConnectionPool {

  @volatile
  var instance: Option[JDBCConnectionPool] = None

  def apply(maxConnection: Int): JDBCConnectionPool = {
    if (instance.nonEmpty) instance.head
    else {
      // Double check locking mechanism
      this synchronized {
        if (instance.nonEmpty) instance.head
        else {
          instance = Some(new JDBCConnectionPool(new FixedConnectionPool(maxConnection, JDBCConnectionProvider)))
          instance.head
        }
      }
    }
  }
}
