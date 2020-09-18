package in.kamranali.collections

import java.util.Random

/**
  * Basic Scala Lesson 33 [Options]
  *
  * Ref
  * - https://www.udemy.com/course/rock-the-jvm-scala-for-beginners/learn/lecture/7660732#overview
  */

object OptionsBasics extends App {

  /*
    Options philosophically means the possible absence of a value

    Were invented to deal with unsafe APIs
   */

  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None

  println(myFirstOption) // Some(4)
  println(noOption) // None

  /**
   * Dealing with unsafe Methods
   *
   * Option Type should do the Null checks for us
   */
  def unsafeMethod: String = null // It is suppose to return String but at some code path it returns NULL

  val result1 = Some(unsafeMethod) // Some(unsafeMethod) <=> Some(null) Which is WRONG !! Because Some should always have a valid value inside

  val result2 = Option(unsafeMethod) // Correct Way !!

  println(result2) // None

  /**
    Chained Methods
    - We deal with unsafe methods by chaining them with safe methods
   */
  def backupMethod(): String = "A valid result"

  val chainedResult: Option[String] = Option(unsafeMethod).orElse(Option(backupMethod())) // which means in case `unsafeMethod` is NULL then fall back to `backupMethod`

  /**
    How to Design Unsafe APIs
    - Return Option in case of returning NULLs
   */

  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("A valid Result")

  val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()


  /**
    Functions on Options
   */
  println(myFirstOption.isEmpty) // false
  println(myFirstOption.get) // UNSAFE - Don't use this because `myFirstOption` may be `None` [Its like trying to access null pointer]

  // println(noOption.get) // Throws Exception "NoSuchElementException"

  println(myFirstOption.map(_ * 2)) // Some(8)

  println(myFirstOption.filter(_ > 10)) // None

  // FlatMap requires following Predicate => A => Option[B]
  println(myFirstOption.flatMap( x => Option(x * 10)))

  /**
    Exercise
   */

  val config: Map[String, String] = Map(

    // Values are fetched from somewhere else. So values of host and port might / might not be here
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

  // Solution 1

  // try to establish a connection
  val host: Option[String] = config.get("host")
  val port: Option[String] = config.get("port")

  /*
    Equivalent Imperative Code
    if (h != null)
      if (p != null)
        return Connection.apply(h,p)

    return null
   */
  val connection: Option[Connection] = host.flatMap(h => port.flatMap(p => Connection.apply(h,p)))

  /*
  if (c != null)
    return c.connect
  return null
   */
  val connectionStatus: Option[String] = connection.map(c => c.connect)

  println(connectionStatus) // Some(Connected to Server) Or None

  /*
    Solution - 2: Chaining All
   */
  config.get("host")
    .flatMap(host => config.get("port")
      .flatMap(port => Connection(host, port))
      .map(connection => connection.connect))
    .foreach(println)

  /*
    Solution - 3: for - comprehension
   */
  val connStatus: Option[String] = for {
    host <- config.get("host") // given a host
    port <- config.get("port") // given a port
    connection <- Connection(host, port) // given a connection
  } yield connection.connect

  connStatus.foreach(println)

}
