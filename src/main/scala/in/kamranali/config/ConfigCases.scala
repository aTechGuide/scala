package in.kamranali.config

import java.util.concurrent.TimeUnit

import com.typesafe.config.ConfigFactory

object ConfigCases extends App {

  System.setProperty("system.key", "This value comes from a system property")

  val config = ConfigFactory.load()
  //val config = ConfigFactory.parseResources("application.conf").resolve()

  println("system.key from environment is = " + config.getString("system.key"))

  // Concatenating: Values on the same line are concatenated (for strings and arrays) or merged (for objects).
  println("\nConcatenating")
  println("app.output = " + config.getString("app.output"))

  // Durations
  println("app.timeout = " + config.getDuration("app.timeout", TimeUnit.MINUTES))
  println("app.timeout = " + config.getDuration("app.timeout", TimeUnit.SECONDS))

  println(s"app.timeout = ${config.getDuration("app.dbtimeout", TimeUnit.SECONDS)}s")
  println(s"app.redis.ttl = ${config.getDuration("app.redis.ttl", TimeUnit.MINUTES)} mins")

  // Inheritance
  println("\nInheritance Example")
  println(s"filter key1 = ${config.getString("filter.key1")}")
  println(s"filter key2 = ${config.getString("filter.key2")}")
  println(s"filter key3 = ${config.getString("filter.key3")}")


  // When two objects are next to each other (close brace of the first on the same line with open brace of the second),
  // they are merged, (shorter way to write "inheritance")
  println("\nInheritance Example 2")
  println("spark-test Name = " + config.getString("spark-test.name"))
  println("spark-test Size = " + config.getString("spark-test.size"))

  println("spark-prod Name = " + config.getString("spark-prod.name"))
  println("spark-prod Size = " + config.getString("spark-prod.size"))

  // Using Include statement we can split configurations across multiple files

  // Handling Array
  println("array_config = " + config.getStringList("array_config.enabled").get(0))

}
