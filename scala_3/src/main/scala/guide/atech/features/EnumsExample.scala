package guide.atech.features

object EnumsExample {

  enum Colors(val color: String) {
    case RED extends Colors("Red")
    case GREEN extends Colors("Green")
    case BLUE extends Colors("Blue")
  }

  def main(args: Array[String]): Unit = {
    println(Colors.RED)
    println(Colors.GREEN)
    println(Colors.BLUE)
  }
}
