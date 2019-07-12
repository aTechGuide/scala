package in.kamranali.AdvanceTypeSystem

/*
  Structural Types OR Type Refinements OR Compile Type Duck Typing
 */
object StructuralTypes extends App {

  /*
    Structural Types
   */
  type JavaCloseable = java.io.Closeable

  class HipsterCloseable {
    def close() = println("I'm closing")
  }

  // Let's write a method that will accept `JavaCloseable` and `HipsterCloseable` without duplicating code

  // Option 1
  //def closeQuietly(closeable: JavaCloseable Or HipsterCloseable) //<- Compilation Error

  // Option 2 (via Structural Types)

  // Structural Types: It can have values, variables and methods inside
  type UnifiedCloseable = {
    def close(): Unit
  } // Structural Types

  def closeQuietly(unifiedCloseable: UnifiedCloseable) = unifiedCloseable.close()

  closeQuietly(new JavaCloseable {
    override def close(): Unit = println("closeQuietly")
  })

  closeQuietly(new HipsterCloseable)

  // Even though `HipsterCloseable` and `JavaCloseable` are two COMPLETELY DIFFERENT. They just match the Type Structure that this method requires.

  /*
    TYPE Refinements
   */

  type AdvancedClosable = JavaCloseable {
    def closedSilently(): Unit
  } // `AdvancedClosable` is `JavaCloseable` + `closedSilently()`. `AdvancedClosable` is enriched we call it `TYPE Refinements`

  class AdvancedJavaCloseable extends JavaCloseable {
    override def close(): Unit = println("Java Closes")
    def closedSilently(): Unit = println("Java Closes Silently")
  }

  def closeShh(advancedClosable: AdvancedClosable): Unit = advancedClosable.closedSilently()

  closeShh(new AdvancedJavaCloseable) // Compiler checks that `AdvancedJavaCloseable` originates from `JavaCloseable` and has `closedSilently` method which Refine Type also has

  // if
  class HipsterCloseable1 {
    def close() = println("I'm closing")
    def closedSilently(): Unit = println("Hipster Closes Silently")
  }

  // closeShh(new HipsterCloseable1) //<- Compilation Error because `HipsterCloseable1` does NOT originate from `JavaCloseable`

  /*
    Structural Types as Standalone types
   */
  def altClose(closeable: {def close(): Unit}): Unit = closeable.close()
  // `{def close(): Unit}` is its own type

  /*
    DUCK Typing
   */
  type SoundMaker = {
    def makeSound(): Unit
  }

  class Dog {
    def makeSound(): Unit = println("Bark")
  }

  class Car {
    def makeSound(): Unit = println("vroom")
  }

  // `Dog` and `Car` both confirm to the Structure Defined by `SoundMaker`.

  // We can safely say

  val dog: SoundMaker = new Dog
  val car: SoundMaker = new Car

  // Compiler is fine as long as Types on RHS confirm with the Structure defines on Type of LHS. Called as STATIC DUCK TYPING
  // Duck Typing Test is done at compile time

  // CAVEAT: Structural Types including Type Refinement are based on Reflection.

  /*
    Exercise
   */
  trait CBL[+T] {
    def head: T
    def tail: CBL[T]
  }

  class Human {
    def head: Brain = new Brain
  }

  class Brain {
    override def toString: String = "BRAINS"
  }

  def f[T](somethingWithAHead: {def head: T}): Unit = println(somethingWithAHead.head)

  // Question: If f is compatible with CBL and Human ?? YES

  case object CBNil extends CBL[Nothing] {
    override def head: Nothing = throw new NoSuchElementException
    def tail: CBL[Nothing] = throw new NoSuchElementException
  }

  case class CBCons[T](override val head: T, override val tail: CBL[T]) extends CBL[T]

  f(CBCons(2, CBNil))
  f(new Human) // Compiler knows that Type T is Brain


  // 2.
  object HeadEqualizer {
    type Headable[T] = {def head: T}
    def ===[T](a: Headable[T], b: Headable[T]): Boolean = a.head == b.head
  }

  // Question: HeadEqualizer is compatible with CBL and Human ?? YES

  val brainzList = CBCons(new Brain, CBNil)
  HeadEqualizer.===(brainzList, new Human) // `head` of both argument return `Brain`

  // But it pose a serious Problem which is
  val stringList = CBCons("Brainz", CBNil)
  HeadEqualizer.===(stringList, new Human) // Problem: Cause `head` of stringList returns String and `head` of `Human` return `Brain`

  // This problem happened because Structural Equalization in `a.head == b.head` relies on reflection.
  // Since it is based on Reflection Compiler erases the Type Parameter T in `===[T]`

  // BE careful in using structural Types in methods with Type Parameters

}
