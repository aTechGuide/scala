package in.kamranali.AdvanceTypeSystem

/**
  * Advance Scala Lesson 49 [Self Types]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/11053872
  *
  */

object SelfTypes extends App {
  /*
    SELF Types: Its a way of requiring a Type to be mixed in
   */

  // Suppose we want to enforce a constrain that every singer also plays an instrument

  trait Instrumentalist {
    def play(): Unit
  }

  trait Singer { self: Instrumentalist => //<- Marker at language level that enforces whoever implements `Singer` should implement `Instrumentalist` as well. This Construct is known as `SELF TYPE`
    def sing(): Unit
  }
  // Name `self` is Just a suggestion, we can use `self: Instrumentalist =>` OR `scala: Instrumentalist =>` OR `this: Instrumentalist =>`

  class LeadSinger extends Singer with Instrumentalist {
    override def play(): Unit = println("Play")
    override def sing(): Unit = println("sing")
  }

  /*
      class Vocalist extends Singer { //<- Compilation Error
        override def sing(): Unit = ???
      }
   */

  // Valid Assignment 1
  val jamesHetfield = new Singer with Instrumentalist {
    override def play(): Unit = println("Play")
    override def sing(): Unit = println("sing")
  }

  // Valid Assignment 2
  class Guitarist extends Instrumentalist {
    override def play(): Unit = println("Guitar")
  }

  val ericClapton = new Guitarist with Singer {
    override def sing(): Unit = println("sing")
  }

  /**
    * Self Types Vs Inheritance
    */
  class A
  class B extends A // => B MUST also be an A

  trait T
  trait S { self: T => } // S REQUIRES a T

  // Common Usage
  /**
    CAKE PATTERN known as "Dependency Injection" in Java World
   */

  // CLASSICAL Dependency Injection in JAVA
  class Component { /* API */ }

  class ConcreteComponentA extends Component
  class ConcreteComponentB extends Component

  class DependentComponent( val component: Component ) // <- this will receive either `ConcreteComponentA` OR `ConcreteComponentB` at Runtime

  // Dependency Injection In Scala
  trait ScalaComponent {
    // API
    def action(x: Int): String
  }

  trait ScalaDependentComponent { self: ScalaComponent =>
    def dependentAction(x: Int): String = action(x) + "this rocks" //<- We are calling `action(x)` as if it was `ScalaDependentComponent` own method
  }

  /**
    Example: We have a Server Side Rendering App.
   */
  // Let's Model elements of the Page on the backend

  // layer 1 of small components
  trait Picture extends ScalaComponent
  trait Stats extends ScalaComponent


  // layer 2 - compose components (we choose what component from previous layer we want to mix in )
  trait Profile extends ScalaDependentComponent with Picture
  trait Analytics extends ScalaDependentComponent with Stats

  // layer 3
  trait ScalaApplication { self: ScalaDependentComponent => }

  trait AnalyticsApp extends ScalaApplication with Analytics

  /*
     Cake Pattern VS Dependency Injection
      - In DI, Dependencies are injected by a framework at Runtime
      - In Cake Pattern, Dependencies are checked at Compile Time
   */

  /**
    Self Types allow us to define seemingly Cyclical Dependencies
   */

  /*
      class X extends Y
      class Y extends X

      Compilation Error : Error:(102, 11) illegal cyclic reference
   */

  // Following code will compile BECAUSE Cyclic Dependency is only Apparent
  trait X {self: Y =>}
  trait Y {self: X =>}
}
