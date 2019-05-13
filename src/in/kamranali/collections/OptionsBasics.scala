package in.kamranali.collections

import java.util.Random

object OptionsBasics extends App {

  /*
  Options philosophically means the possible absence of a value

  Were invented to deal with unsafe APIs
   */

  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None

  println(myFirstOption)
  println(noOption)

  def unsafeMethod: String = null

  val result1 = Some(unsafeMethod) // Some(unsafeMethod) <=> Some(null) Which is WRONG !! Because Some should always have a valid value inside

  val result2 = Option(unsafeMethod) // Correct Way !!

  println(result2)

  /*
  Chained Methods
   */
  def backupMethod(): String = "A valid result"

  val chainedResult = Option(unsafeMethod).orElse(Option(backupMethod())) // which means in case `unsafeMethod` is NULL then fall back to `backupMethod`

  /*
  Design Unsafe APIs

  Return Option in case of returning NULLs
   */

  def betterUnsafeMethod(): Option[String] = None
  def betterBackuoMethod(): Option[String] = Some("A valid Result")

  val betterChainedResult = betterUnsafeMethod() orElse betterBackuoMethod()


  /*
  Functions on Options
   */
  println(myFirstOption.isEmpty)
  println(myFirstOption.get) // UNSAFE - Don't use this because `myFirstOption` may be `None`

  // println(noOption.get) // Throws Exception "NoSuchElementException"

  println(myFirstOption.map(_ * 2))

  println(myFirstOption.filter(_ > 10))

  println(myFirstOption.flatMap( x => Option(x * 10)))

  /*
  Exercise
   */

  val config: Map[String, String] = Map(

    // Values are fetched from somewhere else
    "host" -> "172.43.54.65",
    "port" -> "80"
  )

  class Connection {
    def connect = "Connected to Server"
  }

  object Connection {

    val random = new Random(System.nanoTime())

    def apply(host: String, port: String): Option[Connection] = {
      if (random.nextBoolean()) Some(new Connection)
      else None
    }
  }

  // try to establish a connection
  val host = config.get("host") // returns Option
  val port = config.get("port") // returns Option

  /*
  Equivalent Imperative Code
  if (h != null)
    if (p != null)
      return Connection.apply(h,p)

  return null
   */
  val connection = host.flatMap(h => port.flatMap(p => Connection.apply(h,p)))

  /*
  if (c != null)
    return c.connet
  return null
   */
  val connectionStatus = connection.map(c => c.connect)

  println(connectionStatus)
  connectionStatus.foreach(println)

  /*
  Chaining All
   */
  config.get("host")
    .flatMap(host => config.get("port")
      .flatMap(port => Connection(host, port))
    .map(connection => connection.connect))
    .foreach(println)

  /*
  for - comprehension
   */
  val connStatus = for {
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host, port)
  } yield connection.connect

  connStatus.foreach(println)

}
