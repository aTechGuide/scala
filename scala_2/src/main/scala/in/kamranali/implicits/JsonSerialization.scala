package in.kamranali.implicits

import java.util.Date

/**
  * Advance Scala Lesson 39 [Json Serialization]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/11053838
  */

object JsonSerialization extends App {

  case class User(name: String, age: Int, email: String)
  case class Post(content: String, createdAt: Date)
  case class Feed(user: User, posts: List[Post])


  /*
    We want our code to be extensible + people can contribute. So we are not writing its serializers in class itself.
   */

  /**
    We will use TypeClass Pattern
   */

  // Step 1: Create intermediate data types which can be stringify to Json from primitive types e.g. Int, String, Date etc
  sealed trait JSONValue { // <- intermediate data type
    def stringify: String
  }

  final case class JSONString(value: String) extends JSONValue {
    override def stringify: String =
      "\"" + value + "\""
  }

  final case class JSONNumber(value: Int) extends JSONValue {
    override def stringify: String = value.toString
  }

  final case class JSONArray(values: List[JSONValue]) extends JSONValue {
    override def stringify: String = values.map(_.stringify).mkString("[", ",", "]")
  }

  final case class JSONObject(values: Map[String, JSONValue]) extends JSONValue {

    /*
      {
        name: "john"
        age: 22
        friends: [ ... ]
        latestPost: {
          content: "Scala"
          date: ...
          }
      }

      in above
      name -> key of map
      "john" -> value of map (JSONValue)
     */

    override def stringify: String = values.map {
      case (key, value) => "\"" + key + "\":" + value.stringify
    }.mkString("{", ",", "}")

  }

  // Testing
  val data: JSONObject = JSONObject(Map(
    "user" -> JSONString("Daniel"),
    "Post" -> JSONArray(List(
      JSONString("Scala Rocks!"),
      JSONNumber(453)
    ))
  ))

  println(data.stringify)

  // Step 2: Create Type classes for conversions to intermediate data types
  /*
    We need
    - Type Class
    - Type class instances ()implicit
    - pimp library to use type class instances
   */

  // Type class
  trait JSONConverter[T] {
    def convert(value: T): JSONValue
  }

  // Type class instances
  implicit object StringConverter extends JSONConverter[String] {
    override def convert(value: String): JSONValue = JSONString(value)
  }

  // Type class instances
  implicit object NumberConverter extends JSONConverter[Int] {
    override def convert(value: Int): JSONValue = JSONNumber(value)
  }

  // Type class instances for custom dataTypes
  implicit object UserConverter extends JSONConverter[User] {
    override def convert(user: User): JSONValue = JSONObject(Map(
      "name" -> JSONString(user.name),
      "age" -> JSONNumber(user.age),
      "email" -> JSONString(user.email)
    ))
  }

  implicit object PostConverter extends JSONConverter[Post] {
    override def convert(post: Post): JSONValue = JSONObject(Map(
      "content" -> JSONString(post.content),
      "created:" -> JSONString(post.createdAt.toString)
    ))
  }

  implicit object FeedConverter extends JSONConverter[Feed] {
    override def convert(feed: Feed): JSONValue = JSONObject(Map(
      //"user" -> UserConverter.convert(feed.user),
      "user" -> feed.user.toJSON,
      //"posts" -> JSONArray(feed.posts.map(PostConverter.convert))
      "posts" -> JSONArray(feed.posts.map(_.toJSON))
    ))
  }

  // Step 3: conversions
  implicit class JSONOps[T](value: T) {
    def toJSON(implicit converter: JSONConverter[T]): JSONValue = converter.convert(value)
  }

  val now = new Date(System.currentTimeMillis())

  val john = User("John", 34, "john@g.com")
  val feed = Feed(john, List(
    Post("Hello", now),
    Post("Look at this", now),
  ))

  println(feed.toJSON.stringify) // {"user":{"name":"John","age":34,"email":"john@g.com"},"posts":[{"content":"Hello","created:":"Tue Sep 29 22:39:46 IST 2020"},{"content":"Look at this","created:":"Tue Sep 29 22:39:46 IST 2020"}]}

  // Step 3: Serialize intermediate data types to JSON

}
