package guide.atech.algorithms.team.connection_pool

import javax.naming.LimitExceededException
import scala.collection.mutable
import scala.util.{Failure, Success, Try}

class FixedConnectionPoolAdvance[T, P <: ConnectionProvider[T]](limit: Int, provider: P) extends ConnectionPool[T] {

  private val pool = mutable.Queue.empty[T]
  private var loanedConnection = 0

  override def getConnection: Try[T] = getConnection(0)

  def getConnection(timeout: Long): Try[T] = {
    val maxTime = timeout + System.currentTimeMillis()

    this.synchronized {
      if (pool.nonEmpty) {
        Success(pool.dequeue)
      } else if (loanedConnection < limit) {
        // handle case when there is a possibility to create new connection
        loanedConnection = loanedConnection + 1
        provider.createConnection
      } else {
        // we have already created maximum connections, now we need to wait for a connection to return to pool
        while (pool.isEmpty && maxTime < System.currentTimeMillis()) {
          this.wait(timeout)
        }

        if (System.currentTimeMillis() < maxTime && pool.nonEmpty) {
          Success(pool.dequeue)
        } else Failure(new LimitExceededException("Maximum Connection Loaned"))

      }
    }
  }


  override def releaseConnection(connection: T): Unit = this.synchronized {
    loanedConnection = loanedConnection - 1
    pool.enqueue(connection)
  }
}
