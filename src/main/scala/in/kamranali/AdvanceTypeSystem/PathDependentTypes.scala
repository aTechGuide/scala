package in.kamranali.AdvanceTypeSystem

/**
  * Advance Scala Lesson 47[Inner Types and Path Dependent Types]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/11053864
  *
  */

object PathDependentTypes extends App {

  /*
    PATH DEPENDENT TYPE
   */

  class Outer {
    class Inner
    object InnerObject
    type InnerType

    // `Inner` is visible inside declaring scope
    def print(i: Inner): Unit = println(i)
  }

  def aMethod: Int = {
    class HelperClass //<- Definition of class inside a Block/Expression is VALID
    type HelperType = String // Anywhere other than classes and traits we have to define `type` as Aliases

    2
  }

  val o = new Outer
  // val inner = new Inner //<- Compilation Error
  // val inner = new Outer.Inner //<- Compilation Error

  // To access `Inner` we need Outer instance
  // So for Types nested inside classes. like inner classes, inner objects, the class members and the object members and the trait members
  // and so on and so forth are defined PER INSTANCE.
  val inner: o.Inner = new o.Inner // `o.Inner` is a TYPE
  // ^ So in order to reference an inner type we need an outer instance and different instances will mean different inner Types

  val oo = new Outer
  // val otherInner: oo.Inner = new o.Inner //<- Compilation Error: `o.Inner` and `oo.Inner` are different Types

  o.print(inner)
  // oo.print(inner) //<- Compilation Error: Type mis match because of PATH DEPENDENT TYPE

  /**
    All `InnerType` have common Super Type i.e. Outer#Inner
   */

  class Outer1 {
    class Inner1
    def printGeneral(i: Outer1#Inner1): Unit = println(i)
  }

  val o1 = new Outer1
  val oo1 = new Outer1

  val inner1 = new o1.Inner1

  o1.printGeneral(inner1)
  oo1.printGeneral(inner1)

  // RULE:
  // Whenever we want to make sure that objects created or managed by a specific instance of an outer type
  // can NOT be accidentally or purposely interchanged or mixed with the instance created by another outer type then path dependent types are the way to go

  /**
    Exercise: Database keyed by Generic Type
   */
  trait Item[Key]
  trait IntItem extends Item[Int]
  trait StringItem extends Item[String]

//  def get[ItemType](key: Key): ItemType = ???
//
//  get[IntItem](42) // Compile
//  get[StringItem]("42") // Compile
//  get[IntItem]("42") // Compile BUT should give Compilation Error


  // Solution
  trait ItemLike {
    type Key
  }

  trait Item1[K] extends ItemLike {
    type Key = K
  }

  trait IntItem1 extends Item1[Int]
  trait StringItem1 extends Item1[String]

  class ConcreteIntItem1 extends IntItem1
  class ConcreteStringItem1 extends StringItem1

  def get1[ItemType <: ItemLike](key: ItemType#Key): ItemType = ???
  // `ItemType` is Type parametrised with something that has Abstract type member.
  // This Abstract type member can be used as path dependent type as `ItemType#Key`

  get1[IntItem1](42) // Compile
  get1[StringItem1]("42") // Compile

  //get1[IntItem1]("42") // Compilation Error
}
