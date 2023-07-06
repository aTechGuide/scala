package guide.atech.algorithms.team.connection_pool

import munit.FunSuite

import javax.naming.LimitExceededException
import scala.util.{Failure, Success, Try}


class ConnectionPoolTest extends FunSuite {
  test("it should be able to borrow the connection") {
    /*
      Responsibilities of the class
        - Create the connection object (delegate it to another class)
        - It should cache the connection object once created for future use
        - Limit the connection object pool

    */
    case class Connection(name: String) // T
    case class JDBCConnectionProvider(username: String, password: String, url: String) extends ConnectionProvider[Connection] {
      override def createConnection: Try[Connection] = Success(Connection("JDBC Connection"))
    }

    val jdbcCp = JDBCConnectionProvider("", "", "")
    val fc = new FixedConnectionPool(2, jdbcCp)
    val conn = fc.getConnection

    assertEquals(conn, Success(Connection("JDBC Connection")))
  }

  test("it should through exception when limit is reached") {
    case class Connection(name: String) // T
    case class JDBCConnectionProvider(username: String, password: String, url: String) extends ConnectionProvider[Connection] {
      override def createConnection: Try[Connection] = Success(Connection("JDBC Connection"))
    }

    val jdbcCp = JDBCConnectionProvider("", "", "")
    val fc = new FixedConnectionPool(0, jdbcCp)
    val conn = fc.getConnection

    if(conn.isSuccess) fail("Expected Failure")
    else {
      assertEquals(conn.asInstanceOf[Failure[LimitExceededException]].exception.getMessage, "Maximum Connection Loaned")
    }
  }

  test("it should return cached connection") {
    case class Connection(name: String) // T
    case class JDBCConnectionProvider(username: String, password: String, url: String) extends ConnectionProvider[Connection] {
      override def createConnection: Try[Connection] = Success(Connection("JDBC Connection"))
    }

    val jdbcCp = JDBCConnectionProvider("", "", "")
    val fc = new FixedConnectionPool(1, jdbcCp)
    val conn1 = fc.getConnection

    fc.releaseConnection(conn1.get)
    val conn2 = fc.getConnection

    assert(conn1.get.hashCode() == conn2.get.hashCode())
  }
}
