package guide.atech.designpattern.singleton

object SingletonApp {

  def main(args: Array[String]): Unit = {

    val environment = "Some Environment Context1"
    val environment2 = "Some Environment Context2"

    val appContext = AppContext.getOrCreate(environment)
    println(s"AppContext(env=${appContext.env})") // AppContext(env=Some Environment Context1)

    val appContext2 = AppContext.getOrCreate(environment2) // [Warning] AppContext is already initialized with env = Some Environment Context1
    println(s"AppContext(name=${appContext2.env})") // AppContext(name=Some Environment Context1)
  }
}
