package guide.atech.algorithms.team.connection_pool

import javax.naming.LimitExceededException
import scala.collection.mutable
import scala.util.{Failure, Success, Try}

class FixedConnectionPool[T, P <: ConnectionProvider[T]](limit: Int, provider: P) extends ConnectionPool[T] {

  private val pool = mutable.Queue.empty[T]
  private var loanedConnection = 0

  override def getConnection: Try[T] =
    if (loanedConnection == limit) Failure(new LimitExceededException("Maximum Connection Loaned"))
    else if (pool.nonEmpty) {
      this.synchronized {
        Success(pool.dequeue())
      }
    }
    else {
      this.synchronized {
        loanedConnection = loanedConnection + 1
        provider.createConnection
      }
    }


  override def releaseConnection(connection: T): Unit = this.synchronized {
    loanedConnection = loanedConnection - 1
    pool.enqueue(connection)
  }
}
