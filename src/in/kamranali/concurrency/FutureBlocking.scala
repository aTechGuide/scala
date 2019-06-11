package in.kamranali.concurrency

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

      val transactionStatusFuture = for {
        user <- fetchUser(username)
        transation <- createTransaction(user, merchantName, cost)
      } yield transation.status


      // Blocking in a future
      Await.result(transactionStatusFuture, 2.seconds) // implicit conversions -> pimp my library
      // if duration has passed it will throw an exception with a timeout
    }
  }

  println(BankingApp.purchase("Daniel", "Iphone 12", "Store", 3000))

}
