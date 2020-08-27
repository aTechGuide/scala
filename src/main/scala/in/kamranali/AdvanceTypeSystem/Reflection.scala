package in.kamranali.AdvanceTypeSystem

object Reflection extends App {

  //  How to instantiate a class Or invoke a method by calling its name DYNAMICALLY at Runtime ??
    // This is problem of Reflection, which is the ability of JVM to operate on classes and instances and call methods at Runtime.

  case class Person(name: String) {
    def sayMyName(): Unit = println(s"Hi, my name is $name")
  }

  // Step = 0 (Do the import)
  import scala.reflect.runtime.{universe => ru}

  // Step = 1 instantiate Mirror (which is something which reflect things)
  val m = ru.runtimeMirror(getClass.getClassLoader) //<- `getClass.getClassLoader` returns Class Loader for `Reflection`

  // Step = 2 Create class object by Name
  val clazz = m.staticClass("in.kamranali.AdvanceTypeSystem.Reflection.Person")
  // `clazz` is a `ClassSymbol` i.e. its kind of like a description of class

  // Step 3 create a reflected mirror
  val cm = m.reflectClass(clazz)
  // `cm` is a `ClassMirror` which can access the members of the `ClassSymbol` e.g. constructors and can do things

  // Step 4 get the constructor
  val constructor = clazz.primaryConstructor.asMethod
  // `constructor` is a `MethodSymbol` describing method params etc

  //Step 5 Reflect the constructor
  val constructorMirror = cm.reflectConstructor(constructor)
  // `constructorMirror` is a Method mirror

  // Step 6 Invoke the constructor
  val instance = constructorMirror.apply("John")

  // Basically we instantiated a dynamically computed class Name at Runtime with some arguments
  println(instance)

  // USE CASE 2
  // Suppose I have an instance which I have received over the wire as serialized object.
  val p = Person("Mary") //<- We don't know the type of `p`

  // Method name is computed somewhere else
  val methodName = "sayMyName"

  // 1. mirror
  // 2. reflect the instance
  val reflected = m.reflect(p)

  // 3. method symbol
  val methodSymbol = ru.typeOf[Person].decl(ru.TermName(methodName)).asMethod

  // 4 Reflect the method
  val method = reflected.reflectMethod(methodSymbol)

  // 5 invoke
  method.apply()
}
