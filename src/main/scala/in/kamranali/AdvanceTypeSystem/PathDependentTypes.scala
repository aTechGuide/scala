package in.kamranali.AdvanceTypeSystem

object PathDependentTypes extends App {

  /*
    PATH DEPENDENT TYPE
   */

  class Outer {
    class Inner
    object InnerObject
    type InnerType

    // `Inner` is visible inside declaring scope
    def print(inner: Inner) = println(inner)
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
  val inner = new o.Inner // `o.Inner` is a TYPE

  val oo = new Outer
  // val otherInner: oo.Inner = new o.Inner //<- Compilation Error: `o.Inner` and `oo.Inner` are different Types

  o.print(inner)
  // oo.print(inner) //<- Compilation Error: Type mis match because of PATH DEPENDENT TYPE

  /*
    All `InnerType` have common Super Type i.e. Outer#Inner
   */

  class Outer1 {
    class Inner1
    def printGeneral(i: Outer1#Inner1) = println(i)
  }

  val o1 = new Outer1
  val oo1 = new Outer1

  val inner1 = new o1.Inner1

  o1.printGeneral(inner1)
  oo1.printGeneral(inner1)

  /*
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


  // Sol
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

  def get1[ItemType <: ItemLike](key: ItemType#Key, key2: ItemType): ItemType = key2
  // `ItemType` is Type parametrised with something that has Abstract type member.
  // This Abstract type member can be used as path dependent type as `ItemType#Key`

  get1[IntItem1](42, new ConcreteIntItem1) // Compile
  get1[StringItem1]("42", new ConcreteStringItem1) // Compile

  //get1[IntItem1]("42", new ConcreteIntItem1) // Compilation Error
}
