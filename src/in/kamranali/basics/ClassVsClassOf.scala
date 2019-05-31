package in.kamranali.basics

object ClassVsClassOf extends App {

  /*

    - classOf is a method defined in the Scala Predef object
    - Class[T] is a type like String; classOf[T] is a value of this type like new String
   */

  val aStringClass = classOf[String]

  println(aStringClass)

}
