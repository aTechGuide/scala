package in.kamranali.basics

import java.lang
import java.util.Random

import scala.util.{Failure, Success, Try}

/*

  Golden Rule:
  - If we have a hunch that our code may return a NULL use options
  - If we have a hunch that our code might throw Exceptions use Try

 */
object handlingExceptions extends App {

  /*
  Try is a wrapper for computation that might fail or NOT
   */

  /*
  creating Success and Failure
   */

  val aSuccess = Success(3)
  val aFailure = Failure(new RuntimeException("Failure Message"))

  println(aSuccess)
  println(aFailure)

  /*
  We don't need to construct Success/Failure ourself
  Becayse Try companion objects apply method takes care of it
   */

  def unsafeMethod(): String = throw new RuntimeException("No String")
  val potentialFailure = Try(unsafeMethod())

  println(potentialFailure)

  // Syntax Sugar

  val anotherPotentialFailure = Try {
    // code that might throw
  }

  /*
  Utilities
   */
  println(potentialFailure.isSuccess)

  // orElse

  def backupMethod(): String = "A valid result"

  val fallbackTry = Try(unsafeMethod()).orElse(Try(backupMethod()))

  println(fallbackTry)

  /*
   If our API may throw an exception, we should wrap it in Try Instead
   */

  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException)
  def betterBackUpMethod(): Try[String] = Success("Valid Result")

  val betterFalllback = betterUnsafeMethod() orElse betterBackUpMethod()

  /*
  Map, flatMap, Filter
   */

  println(aSuccess.map(_ * 2))
  println(aSuccess.flatMap(x => Success(x * 10)))
  println(aSuccess.filter(_ > 10))

  // For comprehensions

  /*
  Exercise
   */
  val host = "localhost"
  val port = "8080"

  def renderHTML(page: String) = println(page)

  class Connection {

    def get(url: String): String = {
      val random = new Random(System.nanoTime())

      if (random.nextBoolean()) "HTML CONTENT"
      else throw new RuntimeException("Flaky Connection: Interrupted")
    }

    def getSafe(url: String): Try[String] = Try(get(url))
  }

  object HttpService {
    val random = new Random(System.nanoTime())

    def getConnection(host: String, port: String): Connection = {
      if (random.nextBoolean()) new Connection
      else throw new lang.RuntimeException("Someone took port")
    }

    def getSafeConnection(host: String, port: String): Try[Connection] = Try(getConnection(host, port))
  }


  val possibleConnection = HttpService.getSafeConnection(host, port)
  val possibleHTML = possibleConnection.flatMap(connection => connection.getSafe("/home"))
  possibleHTML.foreach(renderHTML)

  // Chaining

  HttpService.getSafeConnection(host, port)
    .flatMap(connection => connection.getSafe("/home"))
    .foreach(renderHTML)

  // for comprehendsion
  for {
    connection <- HttpService.getSafeConnection(host, port)
    html <- connection.getSafe("/home")
  } renderHTML(html)

  /*
  Imperative

  try {
    connection = HttpService.getSafeConnection(host, port)
    page = connection.get("/home")
  } catch (exception) {

  }


   */



}
