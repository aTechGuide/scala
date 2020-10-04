package in.kamranali.AdvanceTypeSystem

/**
  * Advance Scala Lesson 45 [Variance Exercise]
  *
  * Ref
  * - https://www.udemy.com/course/advanced-scala/learn/lecture/11053854
  */

object VarianceExercise extends App {

  /*
    Design Invariant, covariant, contravariant Parking of things
    - CLASS: Parking[T](things: List[T])
    - Methods:
      - park(vehicle: T)
      - impound(vehicles: List[T])
      - checkVehicles(conditions: String): List[T]

    Make parking a Monad
    - Add flatMap Method
   */

  class Vehicle
  class Bike extends Vehicle
  class Car extends Vehicle

  // Invariant Version
  class IParking[T](vehicles: List[T]) {
    def park(vehicle: T): IParking[T] = ???
    def impound(vehicles: List[T]): IParking[T] = ???
    def checkVehicles(conditions: String): List[T] = ???

    def flatMap[S](f: T => IParking[S]): IParking[S] = ???
  }

  // Covariant Version
  class CParking[+T](vehicles: List[T]) {
    def park[S >: T](vehicle: S): CParking[S] = ???
    def impound[S >: T](vehicles: List[S]): CParking[S] = ???
    def checkVehicles(conditions: String): List[T] = ???

    def flatMap[S](f: T => CParking[S]): CParking[S] = ???
  }

  // Contravariant Version
  class XParking[-T](vehicles: List[T]) {
    def park(vehicle: T): XParking[T] = ???
    def impound(vehicles: List[T]): XParking[T] = ???
    def checkVehicles[S <: T](conditions: String): List[S] = ???

    def flatMap[R <: T, S](f: R => XParking[S]): XParking[S] = ???
  }

  /*
    RULE OF THUMB:
      - Use Covariance: COLLECTION OF THINGS e.g. Parking as Collection of Vehicles
      - Use Contravariance: GROUP OF ACTIONS e.g. Parking as collection of Actions that we want to perform on Type
   */

  // In this case I would err on the Contravariant
  // because the XParking is more used as a group of actions that we want to apply to vehicles like parking, impounding, checking and all that kind of stuff.

  // OUR Types. Suppose we are given the following API
  class IList[T]

  // Covariant Version
  class CParking2[+T](vehicles: IList[T]) {
    def park[S >: T](vehicle: S): CParking2[S] = ???
    def impound[S >: T](vehicles: IList[S]): CParking2[S] = ???
    def checkVehicles[S >: T](conditions: String): IList[S] = ???
  }

  class XParking2[-T](vehicles: IList[T]) {
    def park(vehicle: T): XParking2[T] = ???
    def impound[S <: T](vehicles: IList[S]): XParking2[S] = ???
    def checkVehicles[S <: T](conditions: String): IList[S] = ???
  }

  // Flat Maps

  /*
    def method(...): SomeType
      that SomeType is in a covariant position. Here are the cases:
      - If SomeType is a normal type (e.g. Person), then you're good.
      - If SomeType is a generic type argument (e.g. T), then T must at least not be contravariant in the class where the method is being defined. Invariant and covariant is fine.
      - If SomeType is a generic type containing your class' type argument (e.g. List[T]), then the position of SomeType is accepted (regardless of whether T is annotated with +/-, much like case 1 above),
        but the variance position is now transferred to T. This is the complex part.
        - If SomeType is covariant in T (e.g. List), then SomeType's variance position transfers to T. In this case, T would be in a covariant position.
        - If SomeType is contravariant in T, then SomeType's variance position is transferred backwards to T. In this (your) case, T will be in a contravariant position, which matches the original type definition.
        - If SomeType is invariant in T, then the position of T is strictly invariant, and T must not have any +/- annotation. (edited)
   */
  // Point 1
  class ABC1
  class Class1[T] { def myMethod: ABC1 = ??? } // Invariant Compile
  class Class2[+T] { def myMethod: ABC1 = ??? } // Covariant Compile
  class Class3[-T] { def myMethod: ABC1 = ??? } // Contravariant Compile

  // Point 2
  class Class4[T] { def myMethod: T = ??? } // Invariant Compile
  class Class5[+T] { def myMethod: T = ??? } // Covariant Compile
  // class Class6[-T] { def myMethod: T = ??? } // Contravariant, doesn't Compile. Need to do WIDENING
  // [contravariant type T occurs in covariant position]
  class Class6[-T] { def myMethod[S <: T]: S = ??? }  // Solution

  // Point 3 (case a)
  class ABC2[+K] // ABC is Covariant in K, So return Type is in Covariant Position
  class Class7[T] { def myMethod: ABC2[T] = ??? } // Invariant Compile
  class Class8[+T] { def myMethod: ABC2[T] = ??? } // Covariant Compile
  // class Class9[-T] { def myMethod: ABC2[T] = ??? } // Contravariant, doesn't Compile
  // [contravariant type T occurs in covariant position]
  class Class9[-T] { def myMethod[S <: T]: ABC2[S] = ??? } // Solution

  // Point 3 (case b)
  class ABC3[-K] // ABC is contravariant in K, So return Type is transferred backward to T i.e. it is now CONTRAVARIANT
  class Class10[T] { def myMethod: ABC3[T] = ??? } // Invariant Compile
  // class Class11[+T] { def myMethod: ABC3[T] = ??? } // Covariant, doesn't Compile
  // [covariant type T occurs in contravariant position]
  class Class11[+T] { def myMethod[S >: T]: ABC3[S] = ??? } // Solution
  class Class12[-T] { def myMethod: ABC3[T] = ??? } // Contravariant Compile

  // Point 3 (case b)
  class ABC4[K] // ABC is contravariant in K, So return Type is strictly invariant
  class Class13[T] { def myMethod: ABC4[T] = ??? } // Invariant Compile
  // class Class14[+T] { def myMethod: ABC4[T] = ??? } // Covariant, doesn't Compile
  // [covariant type T occurs in invariant position]
  class Class14[+T] { def myMethod[S >: T]: ABC4[S] = ??? }
  // class Class15[-T] { def myMethod: ABC4[T] = ??? } // Contravariant, doesn't Compile
  // [contravariant type T occurs in invariant position]
  class Class15[-T] { def myMethod[S <: T]: ABC4[S] = ??? }

}
