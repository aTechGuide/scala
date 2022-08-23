package guide.atech.algorithms.team.connectionpool.provider

import guide.atech.algorithms.team.connectionpool.data.Connection

import scala.util.{Success, Try}

object MongoConnectionProvider extends ConnectionProvider[Connection] {
  override def createConnection(username: String, password: String, connectionString: String, driverClassName: String): Try[Connection] =
    Success(Connection("Mongo-DB"))
}
