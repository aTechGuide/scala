package in.kamranali.implicits

object TypeClassDeeper extends App {

  trait HTMLSerializer[T] { // <- TYPE Class with Type T
    def serialize(value: T): String
  }

  object UserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String = s"<div> ${user.name} (${user.age} yo) <a href=${user.email}/> </div>"
  }

  case class User(name: String, age: Int, email: String) //<- NO `toHTML` method

  implicit class HTMLEnrichment[T](value: T) {
    def toHTML(serializer: HTMLSerializer[T]): String = serializer.serialize(value)
  }

  val john = User("John", 13, "johan@abc.con")
  println(john.toHTML(UserSerializer)) // <- Compiler: println(new HTMLEnrichment[User](john).toHTML(UserSerializer))

  // Please serialize `john` with `UserSerializer`. So Compiler tries to wrap `john` into whatever implicit has the toHTML method taking `UserSerializer`
  // here `toHTML` method is implicit as `john` was wrapped into `HTMLEnrichment[User]`
}
