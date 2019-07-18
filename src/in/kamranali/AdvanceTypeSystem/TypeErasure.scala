package in.kamranali.AdvanceTypeSystem

import scala.reflect.runtime.universe

object TypeErasure extends App {

  /*
    Pain Points of Type Erasure
   */

  // 1. Can NOT differentiate between Generic Types at Runtime
  val numbers = List(1,2,3)
  numbers match {
    case listofStrings: List[String] => println("List of string")
    case listOfNumbers: List[Int] => println("List of numbers")
  }  //<- will print `List of string` even though numbers is List of Integers because of Type Erasures

  // 2. limitations on overloads
//  def processList(list: List[Int]): Int = 43
//  def processList(list: List[String]): Int = 43
    // Compilation Error because both methods have identical definition post compilation

  /*
    TYPE TAGS:
      Scala's reflection API work around which allows carrying the complete Generic Type at Compile time to the Runtime
   */

  case class Person(name: String) {
    def sayMyName(): Unit = println(s"Hi, my name is $name")
  }

  // 0 - Imports
  import scala.reflect.runtime.universe._
  val ttag = typeTag[Person] //<- Returns `typeTag` instance of type `Person`

  // 1 - creating a Type Tag "manually" (Preferred way is to pass TypeTag as implicit parameters)
  println(ttag.tpe)

  class MyMap[K, V]

  // 2 Pass TypeTags as implicit parameters and compiler will take care of creating them for us.
  def getTypeArguments[T](value: T)(implicit typeTag: TypeTag[T]) = typeTag.tpe  match { //<- `typeTag` is created by the compiler when it does implicit inference
    case TypeRef(_, _, typeArguments) => typeArguments
    case _ => List()
  } //<- Basically it returns the List of Generic parameters that `T` type was decorated with

  val myMap = new MyMap[Int, String]
  val typeArgs = getTypeArguments(myMap)
  // Compiler will create a Type Tag of `typeTag: TypeTag[MyMap[Int, String]]` for us. So the types can safely be erased after compilation
    // At runtime I get access of `tpe` member of `TypeTag` that compiler created originally

  println(typeArgs)

  // Since we now have Type information at runtime we can do a bunch of operations

  def isSubType[A, B](implicit ttagA: TypeTag[A], ttagB: TypeTag[B]): Boolean = {
    ttagA.tpe <:< ttagB.tpe
  }

  class Animal
  class Dog extends Animal

  println(isSubType[Dog, Animal]) // prints "true"

  // Another Use Case of Type Tag
    // We can create Method Symbol from TypeTag

  import scala.reflect.runtime.{universe => ru}
  val p = Person("Mary") //<- We don't know the type of `p`
  val methodName = "sayMyName" //<- Method name is computed somewhere else

  // 3. method symbol is calculated from Type Tag
  val methodSymbol = typeTag[Person].tpe.decl(ru.TermName(methodName)).asMethod

  val m = ru.runtimeMirror(getClass.getClassLoader)
  val reflected = m.reflect(p)
  val method = reflected.reflectMethod(methodSymbol)

  // 5 invoke
  method.apply()
}
