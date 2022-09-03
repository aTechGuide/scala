package guide.atech.algorithms.team.connectionpool.provider

import guide.atech.algorithms.team.connectionpool.data.Connection
import scala.util.{Success, Try}

class JDBCConnectionProvider(username: String, password: String, connectionString: String, driverClassName: String) extends ConnectionProvider[Connection] {
  override def createConnection(): Try[Connection] =
    Success(Connection("JDBC"))
}
