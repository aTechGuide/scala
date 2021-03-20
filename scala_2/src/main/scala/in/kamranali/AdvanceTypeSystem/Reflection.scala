package in.kamranali.AdvanceTypeSystem

/**
  * Advance Scala Lesson 52 [Reflection 1]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/11053880
  *
  */

object Reflection extends App {

  /*
     How to instantiate a class Or invoke a method by calling its name DYNAMICALLY at Runtime ??
     This is problem of Reflection, which is the ability of JVM to operate on classes and instances and call methods at Runtime.
   */

  case class Person(name: String) {
    def sayMyName(): Unit = println(s"Hi, my name is $name")
  }

  // Step = 0 (Do the import)
  import scala.reflect.runtime.{universe => ru}

  // Step = 1 Instantiate Mirror (which is something that reflect things)
  val m: ru.Mirror = ru.runtimeMirror(getClass.getClassLoader) //<- `getClass.getClassLoader` returns Class Loader for `Reflection`

  // Step = 2 Create class object by Name
  val clazz:ru.ClassSymbol = m.staticClass("in.kamranali.AdvanceTypeSystem.Reflection.Person")
  // `clazz` is a `ClassSymbol` i.e. its kind of like a description of class

  // Step 3 create a reflected mirror
  val cm: ru.ClassMirror = m.reflectClass(clazz)
  // `cm` is a `ClassMirror` which can access the members of the `ClassSymbol` e.g. constructors, methods and can do things

  // Step 4 get the constructor
  val constructor: ru.MethodSymbol = clazz.primaryConstructor.asMethod
  // `constructor` is a `MethodSymbol` which describes the method i.e. What kind of parameter is gets, What kind of return types it has and so on and so forth.

  //Step 5 Reflect the constructor
  val constructorMirror: ru.MethodMirror = cm.reflectConstructor(constructor)
  // `constructorMirror` is a Method mirror

  // Step 6 Invoke the constructor
  val instance: Any = constructorMirror.apply("John")

  // Basically we instantiated a dynamically computed class Name at Runtime with some arguments
  println(instance) // Person(John)

  // USE CASE 2
  // Suppose I have an instance which I have received over the wire as serialized object.
  val p: Any = Person("Mary") //<- We don't know the type of `p`

  // Method name is computed somewhere else
  val methodName = "sayMyName"

  // 1. Obtain the Mirror
  // 2. reflect the instance
  val reflected: ru.InstanceMirror = m.reflect(p)

  // 3. method symbol
  val methodSymbol: ru.MethodSymbol = ru.typeOf[Person].decl(ru.TermName(methodName)).asMethod

  // 4 Reflect the method
  val method: ru.MethodMirror = reflected.reflectMethod(methodSymbol)

  // 5 invoke
  method.apply() // Hi, my name is Mary
}
