package guide.atech.algorithms.team.connectionpool.provider

import guide.atech.algorithms.team.connectionpool.data.Connection

import scala.util.{Success, Try}

class MongoConnectionProvider(username: String, password: String, connectionString: String, driverClassName: String) extends ConnectionProvider[Connection] {
  override def createConnection(): Try[Connection] =
    Success(Connection("Mongo-DB"))
}
