package guide.atech.algorithms.team.connectionpool.provider

import guide.atech.algorithms.team.connectionpool.data.Connection
import scala.util.Try

trait ConnectionProvider[T] {
  def createConnection(username: String, password: String, connectionString: String, driverClassName: String): Try[T]
}