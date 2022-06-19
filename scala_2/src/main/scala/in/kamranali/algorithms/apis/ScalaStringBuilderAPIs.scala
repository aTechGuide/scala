package in.kamranali.algorithms.apis

object ScalaStringBuilderAPIs extends App {

  val sb = new StringBuilder
  val sdata = "data"

  sb.append(sdata)

  println(sdata.substring(0, 2))
  println(sb.toString())
  println(sb.tail.append("c"))
}
