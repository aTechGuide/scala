package guide.atech.algorithms.team.connectionpool.pools

import guide.atech.algorithms.team.connectionpool.{ConnectionPool, FixedConnectionPool}
import guide.atech.algorithms.team.connectionpool.data.Connection
import guide.atech.algorithms.team.connectionpool.provider.{ConnectionProvider, JDBCConnectionProvider}

class JDBCConnectionPool private (val connectionPool: ConnectionPool[Connection])

object JDBCConnectionPool {

  @volatile
  var instance: Option[JDBCConnectionPool] = None

  def apply(maxConnection: Int, provider: ConnectionProvider[Connection]): JDBCConnectionPool = {
    if (instance.nonEmpty) instance.head
    else {
      // Double check locking mechanism
      this synchronized {
        if (instance.nonEmpty) instance.head
        else {
          instance = Some(new JDBCConnectionPool(new FixedConnectionPool(maxConnection, provider)))
          instance.head
        }
      }
    }
  }
}
