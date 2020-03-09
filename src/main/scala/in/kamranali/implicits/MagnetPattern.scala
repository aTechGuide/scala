package in.kamranali.implicits

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object MagnetPattern extends App {

  /*
    MagnetPattern: It solves problems created by Method Overloading
   */

  class P2PRequest
  class P2PResponse
  class Serializer[T]

  trait Actor {
    def receive(statusCode: Int): Int
    def receive(request: P2PRequest): Int
    def receive(response: P2PResponse): Int

    // def recieve[T](message: T)(implicit serializer: Serializer[T]): Int
    def receive[T: Serializer](message: T): Int

    def receive[T: Serializer](message: T, statusCode: Int): Int

    def receive(future: Future[P2PRequest]): Int

    // ... lots of overloads
  }

  /*
    Problems
   */

  // 1. Type erasure: (i.e. Generics types are erased at compile time)
  // if we define `def recieve(future: Future[P2PResponse]): Int` it will NOT Compile

  // 2. Lifting doesn't works for all overloads (if we need higher order function)

  // val recieveFV = recieve _ //<- `_` can mean statusCode, request, response etc So compiler is confused

  // 3. Code Duplication
    // Prob implementation for all `recieve` methods will be similar

  // 4. Problems in Default arguments and Type inference
    // `Default arguments` can mean statusCode, request, response etc So compiler is confused

  // LETS RE WRITE ABOVE API

  trait MessageMagnet[Result] {
    def apply(): Result
  }

  def receive[R](magnet: MessageMagnet[R]): R = magnet.apply()

  implicit class FromP2PRequest(request: P2PRequest) extends MessageMagnet[Int] { // <- `Int` we wanted originally
    override def apply(): Int = {
      // logic of handling P2P request
      println("Handling P2P Request")
      42
    }
  }

  implicit class FromP2PResponse(response: P2PResponse) extends MessageMagnet[Int] { // <- `Int` we wanted originally
    override def apply(): Int = {
      // logic of handling P2P response
      println("Handling P2P Response")
      24
    }
  }

  receive(new P2PRequest) //<- implicit conversation happening from `P2PRequest` into `MessageMagnet` which will be passed to receive method
  receive(new P2PResponse)

  // a Single `receive` now acts as a CENTRE OF GRAVITY for all Overloads


  /*
    Benefits of MagnetPattern
   */

  // 1: NO Type Erasure Problem: Because compiler looks for implicit conversions BEFORE Types are erased

  implicit class FromResponseFuture(future: Future[P2PResponse]) extends MessageMagnet[Int] { // <- `Int` we wanted originally
    override def apply(): Int = 2
  }

  implicit class FromRequestFuture(future: Future[P2PRequest]) extends MessageMagnet[Int] { // <- `Int` we wanted originally
    override def apply(): Int = 3
  }

  println(receive(Future(new  P2PRequest)))
  println(receive(Future(new P2PResponse)))

  // 2 - lifting works
  trait MathLib {
    def add1(x: Int): Int = x + 1
    def add1(s: String): Int = s.toInt + 1

    // ... other add1 overloads
  }

  // magnets of add1

  trait AddMagnet { // <- Notice NO Type Parameter
    def apply(): Int
  }

  def add1(magnet: AddMagnet): Int = magnet.apply()

  implicit class AddInt(x: Int) extends AddMagnet {
    override def apply(): Int = x + 1
  }

  implicit class AddString(s: String) extends AddMagnet {
    override def apply(): Int = s.toInt + 1
  }

  val addFV = add1 _ //<- If there had been Type Parameter, Compiler can't say to which type this lifted function applies to

  // val receiveFV = receive _ //<- Compiler is confused here as `receive` contains Type Parameters
  // receiveFV(new P2PResponse) // Compiler Confused

  println(addFV(1))
  println(addFV("3"))


  /*
    Drawbacks of MagnetPattern
    - Super verbose
    - Hard to Read
    - Can NOT name Or place default arguments (i.e. can't say receive())
    - Call by name does NOT work correctly
 */

  /*
    Exercise: Prove Call by name does NOT work correctly
   */

  class Handler {
    def handle(s: => String) = {
      println(s)
      println(s)
    }

    // ... other overloads
  }

  // magnetize
  trait HandleMagnet {
    def apply(): Unit
  }

  def handle(magnet: HandleMagnet) = magnet.apply()

  implicit class StringHandle(s: => String) extends HandleMagnet {
    override def apply(): Unit = {
      println(s)
      println(s)
    }
  }

  def sideEffectMethod: String = {
    println("Hello")
    "lol"
  }


  handle(sideEffectMethod )

  // prints
  /*
    Hello
    lol
    Hello
    lol
   */

  handle {
    println("Hello")
    "lol"
  }

  // prints
  /*
  Hello
  lol
  lol
 */

  // Only `lol` is printed twice because only `lol` is converted to `HandleMagnet ` Class








}


