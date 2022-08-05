package guide.atech.features

object EnumsExample {

  enum Colors {
    case RED, GREEN, BLUE
  }

  def main(args: Array[String]): Unit = {
    println(Colors.RED)
    println(Colors.GREEN)
    println(Colors.BLUE)
  }
}
