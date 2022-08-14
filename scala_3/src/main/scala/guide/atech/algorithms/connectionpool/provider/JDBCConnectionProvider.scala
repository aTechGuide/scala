package guide.atech.algorithms.connectionpool.provider

import guide.atech.algorithms.connectionpool.data.Connection

import scala.util.{Success, Try}

object JDBCConnectionProvider extends ConnectionProvider[Connection] {
  override def createConnection(username: String, password: String, connectionString: String, driverClassName: String): Try[Connection] =
    Success(Connection("JDBC"))
}
