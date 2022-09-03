package guide.atech.algorithms.team.connectionpool.pools

import guide.atech.algorithms.team.connectionpool.FixedConnectionPool
import guide.atech.algorithms.team.connectionpool.provider.{JDBCConnectionProvider, MongoConnectionProvider}

object MongoConnectionPool {
  val instance = new FixedConnectionPool(10, new MongoConnectionProvider("", "", "", ""))
}
