package in.kamranali.implicits

object TypeClassContextBounds extends App {

  trait HTMLSerializer[T] { // <- TYPE Class with Type T
    def serialize(value: T): String
  }

  implicit class HTMLEnrichment[T](value: T) {
    def toHTML(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(value)
  }

  implicit object UserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String = s"<div> ${user.name} (${user.age} yo) <a href=${user.email}/> </div>"
  }

  case class User(name: String, age: Int, email: String) //<- NO `toHTML` method

  /*
    Context bounds
   */
  def htmlboilerPlate[T](content: T)(implicit serializer: HTMLSerializer[T]): String =
    s"<html><body> ${content.toHTML(serializer)} </body></html>"

  // Above method can be written as
  def htmlSugar[T: HTMLSerializer](content: T): String =
    s"<html><body> ${content.toHTML} </body></html>"

  // `htmlSugar` is a context bound which is telling the compiler to inject `implicit serializer: HTMLSerializer[T]`
  // In this we have a limitation that we can't pass, `serializer` by Name because compiler inject it for us.

  // which leads us to the following topic


  /*
    implicitly
   */

  case class Permissions(mask: String)
  implicit val defaultPermissions: Permissions = Permissions("0744")

  // In some other part of code if we want to surface out the implicit value of permissions.

  // We can write
  val standardParams = implicitly[Permissions]


  // In our `htmlSugar`, to use serializer by Name we can do the following
  def htmlSugar1[T: HTMLSerializer](content: T): String = {

    val serializer = implicitly[HTMLSerializer[T]]
    s"<html><body> ${content.toHTML(serializer)} </body></html>"
  }

  // So we have best of both worlds






}
