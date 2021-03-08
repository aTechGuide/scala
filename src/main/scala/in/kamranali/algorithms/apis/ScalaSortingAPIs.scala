package in.kamranali.algorithms.apis

object ScalaSortingAPIs extends App {

  val arr = Array(5,2,8,1,4,3,6,7)

  println("Ascending Order")
  println(arr.sorted.mkString(" "))

  println("Descending Order")
  println(arr.sorted(Ordering.fromLessThan[Int]((a, b) => b < a)).mkString(" "))

}
