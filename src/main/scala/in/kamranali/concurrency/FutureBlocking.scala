package in.kamranali.concurrency


/**
  * [Concurrency] Chapter 8 A: Future And Promises
  *
  * Blocking on a Future
  *
  * We block on futures on critical operations e.g.
  * - Bank transfers
  * - Any thing that is transactional like
  *
  * We want to make sure that operation is fully complete before moving on to compute additional value
  */

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object FutureBlocking extends App {

  case class User(name: String)
  case class Transaction(sender: String, receiver: String, amount: Double, status: String)

  object BankingApp {
    val name = "Banking"

    def fetchUser(name: String): Future[User] = Future {
      //simulating fetching from DB
      Thread.sleep(500)
      User(name)

    }

    def createTransaction(user: User, merchantName: String, amount: Double): Future[Transaction] = Future {
      // simulate processes
      Thread.sleep(1000)
      Transaction(user.name, merchantName, amount, "Success")
    }

    def purchase(username: String, item: String, merchantName: String, cost: Double): String = {
      // fetch the user from DB
      // create a transaction
      // WAIT for transaction to finish

      val transactionStatusFuture: Future[String] = for {
        user <- fetchUser(username)
        transation <- createTransaction(user, merchantName, cost)
      } yield transation.status


      // Blocking in a future [We block until future is completed]
      Await.result(transactionStatusFuture, 2.seconds) // implicit conversions -> pimp my library
      // if duration has passed it will throw an exception with a timeout

      // Await.ready(transactionStatusFuture, 2.seconds) //<- Same just returns future or throws exp if time has passed
    }
  }

  println(BankingApp.purchase("Daniel", "Iphone 12", "Store", 3000))

}
