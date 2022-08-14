package guide.atech.algorithms.connectionpool.pools

import guide.atech.algorithms.connectionpool.FixedConnectionPool
import guide.atech.algorithms.connectionpool.provider.{JDBCConnectionProvider, MongoConnectionProvider}

object MongoConnectionPool {
  val instance = new FixedConnectionPool(10, MongoConnectionProvider)
}
