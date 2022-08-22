package guide.atech.algorithms.connectionpool

import guide.atech.algorithms.connectionpool.pools.JDBCConnectionPool
import guide.atech.algorithms.connectionpool.provider.JDBCConnectionProvider

class JDBCConnectionPoolSpec extends munit.FunSuite {

  test("only one instance of JDBC Connection Pool") {

    val pool1 = JDBCConnectionPool(5, JDBCConnectionProvider)
    val pool2 = JDBCConnectionPool(10, JDBCConnectionProvider)

    assertEquals(pool1.connectionPool.maxConnections == 5, true)
    assertEquals(pool2.connectionPool.maxConnections == 5, true)
  }

}
