package in.kamranali.implicits

/**
  * Advance Scala Lesson 38 [Type Class, part 3b]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/11053834
  */

object TypeClassContextBounds extends App {

  trait HTMLSerializer[T] { // <- TYPE Class with Type T
    def serialize(value: T): String
  }

  implicit object UserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String = s"<div> ${user.name} (${user.age} yo) <a href=${user.email}/> </div>"
  }

  implicit class HTMLEnrichment[T](value: T) {
    def toHTML(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(value)
  }


  case class User(name: String, age: Int, email: String) //<- NO `toHTML` method

  /**
    Context bounds
   */
  def htmlBoilerPlate[T](content: T)(implicit serializer: HTMLSerializer[T]): String =
    s"<html><body> ${content.toHTML(serializer)} </body></html>"

  // Above method can be written as
  def htmlSugar[T: HTMLSerializer](content: T): String =
    s"<html><body> ${content.toHTML} </body></html>"

  // `: HTMLSerializer` is a context bound which is telling the compiler to inject `implicit serializer: HTMLSerializer[T]` as second parameter list
  // In this we have a limitation that we can't pass, `serializer` by Name because compiler inject it for us.

  // which leads us to the following topic

  val john = User("John", 43, "john@abc.abc")

  println(john.toHTML) // <div> John (43 yo) <a href=john@abc.abc/> </div>
  println(htmlBoilerPlate(john)) // <html><body> <div> John (43 yo) <a href=john@abc.abc/> </div> </body></html>
  println(htmlSugar(john)) // <html><body> <div> John (43 yo) <a href=john@abc.abc/> </div> </body></html>


  /**
    implicitly
   */

  case class Permissions(mask: String) // <- This class is created in some part of the code
  implicit val defaultPermissions: Permissions = Permissions("0744")

  // In some other part of code if we want to surface out the implicit value of permissions.

  // We can write
  val standardParams: Permissions = implicitly[Permissions]
  // So notice that we use implicit values mostly as implicit parameters into methods and we only use their APIs inside those methods.
  // But if we do need them in some other part of the code it makes sense to and want to surface them out, and the `implicitly` method does exactly that.


  // In our `htmlSugar`, to use serializer by Name we can do the following
  def htmlSugar1[T: HTMLSerializer](content: T): String = {

    val serializer = implicitly[HTMLSerializer[T]]

    // use `serializer
    s"<html><body> ${content.toHTML(serializer)} </body></html>"
  }

  // So we have best of both worlds

}
