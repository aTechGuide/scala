package in.kamranali.patternMatching

import scala.util.Random

/*
  1. Cases are matched in order
  2. If no case matched => throw MatchError
  3. type of Pattern Match => Unification of type returned by all the cases
  4. Pattern Matching on sealed hierarchy (Pattern Matching works really well with case classes)
     Normal class DO NOT come with extractor patters
  5. Decompose values
 */

object PatternMatching extends App {

  // switch on steroids

  val random = new Random
  val x = random.nextInt(10)

  val desciption = x match {
    case 1 => "The ONE"
    case 2 => "Double"
    case 3 => "Third Time"
    case _ => "somethingelse" // _ = Wildcard
  }

  println(x)
  println(desciption)


  // Decompose values (Extracting values)

  case class Person(name: String, age: Int)

  val bob = Person("Bob", 20)

  val greeting = bob match {

    case Person(n, a) if a < 21 => s"Hi, my name is $n and I can't drink in the US"
    case Person(n, a) => s"Hi, my name is $n and I am $a years old"
    case _ => "I don't know who I am"
  }

  println(greeting)

  // Pattern Matching on sealed hierarchy

  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal


  val animal: Animal = Dog("Terra Nova")

  animal match {
    case Dog(someBreed) => println(s"Matched of $someBreed breed")
  }

  // Matching Everything DONT DO THAT
  val isEven = x match {
    case n if n% 2 == 0 => true
    case _ => false
  }

  /*
  Exercise
  - takes an expression and returns human readable form of it e.g.
    - Sum(Number(2), Number(3), Number(4)) => 2 + 3 + 4
    - Prod(Sum(Number(1), Number(2)), Number(2)) => (1 + 2) * 3
   */

  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  def show(e: Expr): String = e match {
    case Number(n) => s"$n"
    case Sum(e1, e2) => show(e1) + " + " + show(e2)
    case Prod(e1, e2) => {
      def mayShowParentheses(exp: Expr) = exp match {
        case Prod(_ , _) => show(exp)
        case Number(_) => show(exp)
        case _ => "(" + show(exp) + ")"
      }

      mayShowParentheses(e1) + " * " + mayShowParentheses(e2)
    }
  }

  println(show(Sum(Number(2), Number(3))))

  println(show(Prod(Sum(Number(1), Number(2)), Number(2))))


}
