package guide.atech.algorithms.connectionpool

import guide.atech.algorithms.connectionpool.provider.ConnectionProvider

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

class FixedConnectionPool[T](maxConnection: Int, connectionProvider: ConnectionProvider[T]) extends ConnectionPool[T] {

  private val pool = mutable.Queue[T]()
  private var size = 0

  def getConnection: Try[T] = getConnection(0)

  def getConnection(timeout: Long): Try[T] = {
    val maxTime = timeout + System.currentTimeMillis()

    this.synchronized {
      if (pool.nonEmpty) {
        Success(pool.dequeue)
      } else if (size < maxConnection) {
        size = size + 1
        connectionProvider.createConnection("", "", "", "")
      } else {
        // we have already created maximum connections
        while (pool.isEmpty && maxTime < System.currentTimeMillis()) {
          this.wait(timeout)
        }

        if (maxTime < System.currentTimeMillis()) {
          Success(pool.dequeue)
        } else Failure(new RuntimeException("Timeout"))

      }
    }

  }

  def releaseConnection(connection: T): Unit = {
    this.synchronized {
      pool.enqueue(connection)
      this.notifyAll()
    }
  }

  def maxConnections: Int = maxConnection

}
