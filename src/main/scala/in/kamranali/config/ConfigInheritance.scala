package in.kamranali.config

import java.io.File

import com.typesafe.config.ConfigFactory

object ConfigInheritance extends App {

  val baseConf = ConfigFactory.load()
  val prodConf = ConfigFactory.load("example.conf")
  val config = ConfigFactory.parseFile(new File("/Users/kamali/mcode/scala/external.json")).withFallback(prodConf)


  println("app.output " + config.getString("app.output"))
  println("app.debug =  " + config.getBoolean("app.debug"))
  println("app.name " + config.getString("app.name"))

}
